//
// Created by Administrator on 2019/11/12.
//

#ifndef ENJOYMMKV_THREADLOCK_H
#define ENJOYMMKV_THREADLOCK_H

#include <pthread.h>

class ThreadLock {
pthread_mutex_t m_lock;
public:
    ThreadLock();
    ~ThreadLock();

    void lock();
    void unlock();
};


#endif //ENJOYMMKV_THREADLOCK_H
