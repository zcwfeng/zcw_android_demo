/*
 * Tencent is pleased to support the open source community by making
 * MMKV available.
 *
 * Copyright (C) 2018 THL A29 Limited, a Tencent company.
 * All rights reserved.
 *
 * Licensed under the BSD 3-Clause License (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *       https://opensource.org/licenses/BSD-3-Clause
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#include "MmapedFile.h"
#include "MMKVLog.h"
#include <errno.h>
#include <fcntl.h>
#include <libgen.h>
#include <sys/mman.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <unistd.h>

using namespace std;

//系统给我们提供真正的内存时，用页为单位提供
//内存分页大小 一分页的大小
const int DEFAULT_MMAP_SIZE = getpagesize();

MmapedFile::MmapedFile(const std::string &path)
        : m_name(path), m_fd(-1), m_segmentPtr(nullptr), m_segmentSize(0) {



    m_fd = open(m_name.c_str(), O_RDWR | O_CREAT, S_IRWXU);
    if (m_fd < 0) {
        LOGE("fail to open:%s, %s", m_name.c_str(), strerror(errno));
    } else {
        //读取文件大小页面
        struct stat st = {0};
        if (fstat(m_fd, &st) != -1) {
            m_segmentSize = st.st_size;
        }
        if (m_segmentSize < DEFAULT_MMAP_SIZE) {
            m_segmentSize = DEFAULT_MMAP_SIZE;
            // 设置文件大小失败或者清理文件内容失败
            if (ftruncate(m_fd, m_segmentSize) != 0 || !zeroFillFile(m_fd, 0, m_segmentSize)) {
                LOGE("fail to truncate [%s] to size %zu, %s", m_name.c_str(),
                     m_segmentSize, strerror(errno));
                close(m_fd);
                m_fd = -1;
                removeFile(m_name);
                return;
            }
        }
        m_segmentPtr = (char *) mmap(0, m_segmentSize, PROT_READ | PROT_WRITE, MAP_SHARED, m_fd, 0);
        //mmap失败
        if (m_segmentPtr == MAP_FAILED) {
            LOGE("fail to mmap [%s], %s", m_name.c_str(), strerror(errno));
            close(m_fd);
            m_fd = -1;
            m_segmentPtr = 0;
        }
    }

}


MmapedFile::~MmapedFile() {
    if (m_segmentPtr != MAP_FAILED && m_segmentPtr != 0) {
        munmap(m_segmentPtr, m_segmentSize);
        m_segmentPtr = 0;
    }
    if (m_fd >= 0) {
        close(m_fd);
        m_fd = -1;
    }
}


bool removeFile(const string &nsFilePath) {
    int ret = unlink(nsFilePath.c_str());
    if (ret != 0) {
        LOGE("remove file failed. filePath=%s, err=%s", nsFilePath.c_str(), strerror(errno));
        return false;
    }
    return true;
}


bool zeroFillFile(int fd, size_t startPos, size_t size) {
    if (fd < 0) {
        return false;
    }

    if (lseek(fd, startPos, SEEK_SET) < 0) {
        LOGE("fail to lseek fd[%d], error:%s", fd, strerror(errno));
        return false;
    }

    static const char zeros[4096] = {0};
    while (size >= sizeof(zeros)) {
        // 写入空数据
        if (write(fd, zeros, sizeof(zeros)) < 0) {
            LOGE("fail to write fd[%d], error:%s", fd, strerror(errno));
            return false;
        }
        size -= sizeof(zeros);
    }
    if (size > 0) {
        if (write(fd, zeros, size) < 0) {
            LOGE("fail to write fd[%d], error:%s", fd, strerror(errno));
            return false;
        }
    }
    return true;
}
