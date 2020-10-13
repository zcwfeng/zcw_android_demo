#ifndef MMKV_MMAPEDFILE_H
#define MMKV_MMAPEDFILE_H

#include <string>
#include <sys/ioctl.h>
#include <sys/mman.h>


extern const int DEFAULT_MMAP_SIZE;

class MmapedFile {
    std::string m_name;
    int m_fd;
    void *m_segmentPtr;
    size_t m_segmentSize;

    MmapedFile(const MmapedFile &other) = delete;

    MmapedFile &operator=(const MmapedFile &other) = delete;

public:
    MmapedFile(const std::string &path);

    ~MmapedFile();

    size_t getFileSize() { return m_segmentSize; }

    void *getMemory() { return m_segmentPtr; }

    std::string &getName() { return m_name; }

    int getFd() { return m_fd; }

    bool isFileValid() {
        return m_fd >= 0 && m_segmentSize > 0 && m_segmentPtr && m_segmentPtr != MAP_FAILED;
    }
};

extern bool removeFile(const std::string &nsFilePath);

extern bool zeroFillFile(int fd, size_t startPos, size_t size);

#endif //MMKV_MMAPEDFILE_H
