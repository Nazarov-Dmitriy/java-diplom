package ru.netology.backend.servise;

import org.springframework.stereotype.Service;
import ru.netology.backend.controller.UserFile;
import ru.netology.backend.repository.FileRepository;

import java.util.List;

@Service
public class FileServise {
    private final FileRepository fileRepository;

    public FileServise(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public void addFile(String fileName, long userId) {
        fileRepository.addFile(fileName, userId);
    }

    public List<UserFile> getListUserFiles(long userId, int limit) {
        return fileRepository.getList(userId, limit);
    }

    public void editName(String filename, String ediFilename) {
        fileRepository.editName(filename, ediFilename);
    }

    public void deleteFile(String filename) {
        fileRepository.deleteFile(filename);
    }
}
