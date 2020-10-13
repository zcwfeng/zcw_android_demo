//
// Created by Administrator on 2019/11/12.
//

#include "ThreadLock.h"

ThreadLock::ThreadLock() {
    pthread_mutexattr_t attr;
    pthread_mutexattr_init(&attr);

    //设置为递归锁
    pthread_mutexattr_settype(&attr,PTHREAD_MUTEX_RECURSIVE);
    pthread_mutex_init(&m_lock, &attr);

    pthread_mutexattr_destroy(&attr);

}

ThreadLock::~ThreadLock() {
    pthread_mutex_destroy(&m_lock);
}

void ThreadLock::lock() {
  pthread_mutex_lock(&m_lock);

}

void ThreadLock::unlock() {
    pthread_mutex_unlock(&m_lock);
}