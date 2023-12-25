package ru.netology.backend.utils;

import java.io.File;

 public class FolderCreate {
     static  public void createFolder(String folderPath) {
        File directory = new File(folderPath);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }
}
