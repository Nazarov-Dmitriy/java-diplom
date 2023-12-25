package ru.netology.backend.utils;

import org.springframework.web.multipart.MultipartFile;
import ru.netology.backend.exception.InternalServerError;
import ru.netology.backend.loger.Loger;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class FileCreate {
    public static void createFile(MultipartFile file, String name) {

        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File("files/" + name)));
                stream.write(bytes);
                stream.close();
            } catch (Exception e) {
                Loger.write("ERROR", "Вам не удалось загрузить " + name + "ошибка " + e.getMessage());
                throw new InternalServerError(" Error getting file list");
            }
        }
    }
}
