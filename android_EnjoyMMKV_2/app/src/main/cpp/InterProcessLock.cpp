
#include "InterProcessLock.h"
#include "MMKVLog.h"
#include <sys/file.h>
#include <unistd.h>

static int LockType2FlockType(LockType lockType) {
    switch (lockType) {
        case SharedLockType:
            return LOCK_SH; //共享锁，读
        case ExclusiveLockType:
            return LOCK_EX; //互斥锁，写
    }
    return LOCK_EX;
}


bool FileLock::doLock(LockType lockType, bool wait) {
    if (!isFileLockValid()) {
        return false;
    }
    bool unLockFirstIfNeeded = false;
    //读锁
    if (lockType == SharedLockType) {
//        flock(写锁); // 还在用写锁！
//        flock(读锁); // 这时候上读锁,降级了！！ 但是写锁还在用，所以不能降级
        m_sharedLockCount++;
        // 如果本进程之前被上过读锁或者写锁 还未释放，那么不再加读锁
        if (m_sharedLockCount > 1 || m_exclusiveLockCount > 0) {
            return true;
        }
    } else {
        //写锁
        m_exclusiveLockCount++;
        // 如果本进程之前上过写锁还未释放
        if (m_exclusiveLockCount > 1) {
            return true;
        }
        // 如果当前已经持有读锁，那么先尝试加写锁，
        // try_lock 失败说明其他进程持有了读锁，需要先将自己的读锁释放掉，再进行加写锁操作，以免其他进程也在请求加写锁造成死锁
        if (m_sharedLockCount > 0) {
            unLockFirstIfNeeded = true;
        }
    }

    int realLockType = LockType2FlockType(lockType);
    // LOCK_NB： 不阻塞
    int cmd = wait ? realLockType : (realLockType | LOCK_NB);

    if (unLockFirstIfNeeded) {
        // try lock，这里肯定就是 LOCK_EX|LOCK_NB ，
        auto ret = flock(m_fd, realLockType | LOCK_NB);
        if (ret == 0) { //加锁成功
            return true;
        }
        // 加锁失败, 先把自己的读锁释放
         flock(m_fd, LOCK_UN);
    }

    auto ret = flock(m_fd, cmd); //加锁lock方法都是阻塞
    if (ret != 0) {
        return false;
    } else {
        return true;
    }
}

bool FileLock::lock(LockType lockType) {
    return doLock(lockType, true);
}

bool FileLock::try_lock(LockType lockType) {
    return doLock(lockType, false);
}

bool FileLock::unlock(LockType lockType) {
    if (!isFileLockValid()) {
        return false;
    }
    bool unlockToSharedLock = false;

    if (lockType == SharedLockType) {
        if (m_sharedLockCount == 0) {
            //没锁解，失败
            return false;
        }
        m_sharedLockCount--;
        // 计数器不为0，不解锁
        if (m_sharedLockCount > 0 || m_exclusiveLockCount > 0) {
            //本次解锁完成
            return true;
        }
    } else {
        if (m_exclusiveLockCount == 0) {
            return false;
        }
        m_exclusiveLockCount--;
        if (m_exclusiveLockCount > 0) {
            return true;
        }
        // 写锁解除完了（计数为0）并且读锁还有计数，还原锁为读锁
        if (m_sharedLockCount > 0) {
            unlockToSharedLock = true;
        }
    }
    //unlockToSharedLock: 为true,需要解写锁，然而读锁还存在！
    int cmd = unlockToSharedLock ? LOCK_SH : LOCK_UN;
    auto ret = flock(m_fd, cmd);
    if (ret != 0) {
        return false;
    } else {
        return true;
    }
}
