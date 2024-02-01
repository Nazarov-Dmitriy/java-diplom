package ru.netology.backend.servise;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.backend.dto.UserFile;
import ru.netology.backend.exception.BadRequest;
import ru.netology.backend.exception.InternalServerError;
import ru.netology.backend.model.FilesModel;
import ru.netology.backend.model.Users;
import ru.netology.backend.repository.FileRepository;
import ru.netology.backend.repository.UserRepository;
import ru.netology.backend.utils.FileCreate;
import ru.netology.backend.utils.FolderCreate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FileServise {
    @Value("${folder-files}")
    private String folderFiles;
    private final FileRepository fileRepository;
    private final UserRepository userRepository;

    public FileServise(FileRepository fileRepository, UserRepository userRepository) {
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<Void> addFile(String fileName, MultipartFile file, HttpServletRequest req) {
        try {
            if (!fileName.isEmpty()) {
                var username = (String) req.getSession().getAttribute("username");
                var path = username + "/" + fileName;
                FolderCreate.createFolder(folderFiles + username);
                FileCreate.createFile(file, path);
                Optional<Users> user = userRepository.findByLogin(username);
                FilesModel build = FilesModel.builder().name(fileName).users(user.get()).build();
                fileRepository.save(build);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                throw new BadRequest("Error input data");
            }
        } catch (EmptyResultDataAccessException e) {
            throw new BadRequest("Error input data");
        }
    }

    public ResponseEntity<Void> deleteFile(String filename, HttpServletRequest req) {
        try {
            if (!filename.isEmpty()) {
                var usernaname = req.getSession().getAttribute("username");
                File file = new File(folderFiles + usernaname + "/" + filename);
                if (file.delete()) {
                    fileRepository.deleteFile(filename);
                } else {
                    throw new InternalServerError("Error delet file");
                }
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                throw new BadRequest("Error input data");
            }
        } catch (EmptyResultDataAccessException e) {
            throw new BadRequest("Error input data");
        }
    }

    public ResponseEntity<Resource> downloadFile(String filename, String folderFiles, HttpServletRequest req) throws FileNotFoundException {
        if (!filename.isEmpty()) {
            var usernaname = (String) req.getSession().getAttribute("username");
            final var filePath = Path.of(folderFiles + usernaname + '/' + filename);
            final String mimeType;
            try {
                mimeType = Files.probeContentType(filePath);
            } catch (IOException e) {
                throw new InternalServerError("Error upload file");
            }
            File file = new File(String.valueOf(filePath));
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName()).contentType(MediaType.valueOf(mimeType)).contentLength(file.length()) //
                    .body(resource);
        } else {
            throw new BadRequest("Error input data");
        }
    }

    public ResponseEntity<Void> editName(String filename, String ediFilename, HttpServletRequest req) {
        try {
            if (!filename.isEmpty() || !ediFilename.isEmpty()) {
                var usernaname = req.getSession().getAttribute("username");
                File file = new File(folderFiles + usernaname + "/" + filename);
                File newFile = new File(folderFiles + usernaname + "/" + ediFilename);
                if (file.renameTo(newFile)) {
                    fileRepository.editName(filename, ediFilename);
                } else {
                    throw new InternalServerError("Error upload file");
                }
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                throw new BadRequest("Error input data");
            }
        } catch (EmptyResultDataAccessException e) {
            throw new BadRequest("Error input data");
        }
    }

    public ResponseEntity<List<UserFile>> getListUserFiles(int limit, HttpServletRequest req) {
        try {
            if (limit > 0) {
                List<UserFile> arrFiles = new ArrayList<>();
                var userId = (long) req.getSession().getAttribute("user-id");
                var usernaname = req.getSession().getAttribute("username");
                var files = fileRepository.getList(userId, limit);
                for (FilesModel item : files) {
                    File file = new File(folderFiles + usernaname + "/" + item.getName());
                    arrFiles.add(new UserFile(file.getName(), file.length()));
                }
                return new ResponseEntity<>(arrFiles, HttpStatus.OK);
            } else {
                throw new BadRequest("Error input data");
            }
        } catch (EmptyResultDataAccessException e) {
            throw new BadRequest("Error input data");
        }
    }
}
