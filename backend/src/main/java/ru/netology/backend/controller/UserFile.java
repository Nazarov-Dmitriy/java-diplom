package ru.netology.backend.controller;

import lombok.Getter;

@Getter
public class UserFile {
    String filename;
    long size;

    public UserFile(String filename, long size) {
        this.filename = filename;
        this.size = size;
    }

    public UserFile(String name) {
        this.filename = name;
    }

    @Override
    public String toString() {
        return "UserFile{" +
                "name='" + filename + '\'' +
                ", size=" + size +
                '}';
    }
}
