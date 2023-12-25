package ru.netology.backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.backend.dto.EditFileNameDto;
import ru.netology.backend.exception.BadRequest;
import ru.netology.backend.exception.InternalServerError;
import ru.netology.backend.servise.FileServise;
import ru.netology.backend.utils.FileCreate;
import ru.netology.backend.utils.FolderCreate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;


@RestController

public class FilesController {
    private String folderFiles = "files/";
    private FileServise fileServise;

    public FilesController(FileServise fileServise) {
        this.fileServise = fileServise;
    }

    @GetMapping("/list")
    public ResponseEntity<?> getFiles(@RequestParam int limit, HttpServletRequest req) {
        if (limit > 0) {
            ArrayList<UserFile> arrFiles = new ArrayList<>();
            var userId = (long) req.getSession().getAttribute("user-id") ;
            var usernaname = req.getSession().getAttribute("username");
            var files = fileServise.getListUserFiles(userId, limit);
            for (UserFile item : files) {
                File file = new File(folderFiles + usernaname + "/" + item.getFilename());
                arrFiles.add(new UserFile(file.getName(), file.length()));
            }
            return new ResponseEntity<>(arrFiles, HttpStatus.OK);
        } else {
            throw new BadRequest("Error input data");
        }
    }

    @PostMapping("/file")
    @ResponseBody
    public ResponseEntity<?> uploadFile(@RequestParam String filename, @RequestPart MultipartFile file, HttpServletRequest req) {
        if (!filename.isEmpty()) {
            var usernaname = req.getSession().getAttribute("username");
            long userId = (long) req.getSession().getAttribute("user-id");
            var path = usernaname + "/" + filename;
            FolderCreate.createFolder(folderFiles + usernaname);
            FileCreate.createFile(file, path);
            fileServise.addFile(filename, userId);
            return new ResponseEntity<>( HttpStatus.OK);
        } else {
            throw new BadRequest("Error input data");
        }

    }

    @PutMapping("/file")
    @ResponseBody
    public ResponseEntity<Object> editFile(@RequestParam String filename, @RequestBody EditFileNameDto body, HttpServletRequest req) {
        if (!filename.isEmpty() || !body.getFilename().isEmpty()) {
            var usernaname = req.getSession().getAttribute("username");

            File file = new File(folderFiles + usernaname + "/" + filename);
            File newFile = new File(folderFiles + usernaname + "/" + body.getFilename());

            if (file.renameTo(newFile)) {
                fileServise.editName(filename, body.getFilename());
            } else {
                throw new InternalServerError("Error upload file");
            }
            return new ResponseEntity<>( HttpStatus.OK);
        } else {
            throw new BadRequest("Error input data");
        }
    }

    @DeleteMapping("/file")
    public ResponseEntity<Object> deleteFile(@RequestParam String filename, HttpServletRequest req) {
        if (!filename.isEmpty()) {
            var usernaname = req.getSession().getAttribute("username");

            File file = new File(folderFiles + usernaname + "/" + filename);
            if (file.delete()) {
                fileServise.deleteFile(filename);
            } else {
                throw new InternalServerError("Error delet file");
            }
            return new ResponseEntity<>( HttpStatus.OK);
        } else {
            throw new BadRequest("Error input data");
        }
    }

    @GetMapping("/file")
    public ResponseEntity<InputStreamResource> downloadFile(@RequestParam String filename, HttpServletRequest req) throws FileNotFoundException {
        if (!filename.isEmpty()) {
            var usernaname = req.getSession().getAttribute("username");
            final var filePath = Path.of(folderFiles + usernaname + '/' + filename);
            final String mimeType;

            try {
                mimeType = Files.probeContentType(filePath);
            } catch (IOException e) {
                throw new InternalServerError(" Error upload file");
            }


            File file = new File(String.valueOf(filePath));
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                    .contentType(MediaType.valueOf(mimeType))
                    .contentLength(file.length()) //
                    .body(resource);
        } else {
            throw new BadRequest("Error input data");
        }
    }
}

