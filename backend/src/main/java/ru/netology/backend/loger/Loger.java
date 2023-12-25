package ru.netology.backend.loger;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Loger {
    static Date time = new Date();
    static SimpleDateFormat dt1 = new SimpleDateFormat("HH:mm:ss");
    static String dtime = dt1.format(time);
    static String fileLog = "file.log";

    public static void write(String level, String text) {
        try (FileWriter writer = new FileWriter(fileLog, true)) {
            writer.write("[" + level + "] " + dtime + " " + text + "\n");
        } catch (IOException e) {
            Loger.write("ERROR", " " + e.getMessage());
        }
    }
}
