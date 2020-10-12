/**
 * @author Lance
 * @date 2019-06-11
 */

#ifndef MMKV_SCOPEDLOCK_H
#define MMKV_SCOPEDLOCK_H

/**
 * 利用C++类的构造与析构函数自动加锁与释放
 * @tparam T
 */
template<typename T>
class ScopedLock {
    T *m_lock;

    //表示禁止使用  编译器的拷贝默认构造函数和默认的=操作符
    ScopedLock(const ScopedLock<T> &other) = delete;

    ScopedLock &operator=(const ScopedLock<T> &other) = delete;

public:
    ScopedLock(T *oLock) : m_lock(oLock) {
        lock();
    }

    ~ScopedLock() {
        unlock();
        m_lock = nullptr;
    }

    void lock() {
        if (m_lock) {
            m_lock->lock();
        }
    }

    bool try_lock() {
        if (m_lock) {
            return m_lock->try_lock();
        }
        return false;
    }

    void unlock() {
        if (m_lock) {
            m_lock->unlock();
        }
    }
};

// __COUNTER__ 表示这个宏函数被调用几次，整型值
#define SCOPEDLOCK(lock) _SCOPEDLOCK(lock, __COUNTER__)
//编译前才处理，将__COUNTER__ 变为具体的值，而这里如果没有这个玩意过度，则编辑时候会直接被识别为 __COUNTER__
#define _SCOPEDLOCK(lock, counter) __SCOPEDLOCK(lock, counter)
#define __SCOPEDLOCK(lock, counter) ScopedLock<decltype(lock)> __scopedLock##counter(&lock)

#endif //MMKV_SCOPEDLOCK_H
