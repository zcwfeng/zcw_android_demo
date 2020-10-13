#ifndef MMKV_INTERPROCESSLOCK_H
#define MMKV_INTERPROCESSLOCK_H

#include <assert.h>
#include <fcntl.h>

enum LockType {
    SharedLockType,    //共享锁 读锁，我读你也只能读，不可加写锁
    ExclusiveLockType, //排他锁 写锁，只能一个单位获得
};

/**
 * 封装支持递归锁和锁升降级的文件锁
 * 递归锁
 *   意思是如果一个进程/线程已经拥有了锁，那么后续的加锁操作不会导致卡死，并且解锁也不会导致外层的锁被解掉。
 *   对于文件锁来说，前者是满足的，后者则不然。
 *   因为文件锁是状态锁，没有计数器，无论加了多少次锁，一个解锁操作就全解掉。只要用到子函数，就非常需要递归锁。

 * 锁升级/降级
 *   锁升级是指将已经持有的共享锁，升级为互斥锁，亦即将读锁升级为写锁；锁降级则是反过来。
 *   文件锁支持锁升级，但是容易死锁：
 *      假如 A、B 进程都持有了读锁，现在都想升级到写锁，就会陷入相互等待的困境，发生死锁。
 *   另外，由于文件锁不支持递归锁，也导致了锁降级无法进行，一降就降到没有锁。
 */
class FileLock {

    //文件句柄
    int m_fd;
    //文件锁
    flock m_lockInfo;
    //读计数
    size_t m_sharedLockCount;
    //写计数
    size_t m_exclusiveLockCount;

    bool doLock(LockType lockType, bool wait);

    bool isFileLockValid() { return m_fd >= 0; }

    FileLock(const FileLock &other) = delete;

    FileLock &operator=(const FileLock &other) = delete;

public:
    FileLock(int fd) : m_fd(fd), m_sharedLockCount(0), m_exclusiveLockCount(0) {}


    bool lock(LockType lockType);

    bool try_lock(LockType lockType);

    bool unlock(LockType lockType);
};


/**
 * 文件锁封装为进程读/写锁
 */
class InterProcessLock {
    FileLock *m_fileLock;
    LockType m_lockType;

public:
    InterProcessLock(FileLock *fileLock, LockType lockType)
            : m_fileLock(fileLock), m_lockType(lockType), m_enable(true) {
        assert(m_fileLock);
    }

    bool m_enable;

    void lock() {
        if (m_enable) {
            m_fileLock->lock(m_lockType);
        }
    }

    bool try_lock() {
        if (m_enable) {
            return m_fileLock->try_lock(m_lockType);
        }
        return false;
    }

    void unlock() {
        if (m_enable) {
            m_fileLock->unlock(m_lockType);
        }
    }
};

#endif //MMKV_INTERPROCESSLOCK_H
