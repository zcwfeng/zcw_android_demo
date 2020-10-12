/**
 * @author Lance
 * @date 2019-06-10
 */

#ifndef MMKV_THREADLOCK_H
#define MMKV_THREADLOCK_H

#include <pthread.h>

class ThreadLock {
private:
    pthread_mutex_t m_lock; //linux线程互斥量

public:
    ThreadLock();

    ~ThreadLock();

    void lock();

    bool try_lock();

    void unlock();
};


#endif //MMKV_THREADLOCK_H
