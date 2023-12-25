package ru.netology.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.netology.backend.loger.Loger;
import ru.netology.backend.utils.FolderCreate;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
		FolderCreate.createFolder("files");
		Loger.write("INFO", "Server starter");
	}
}
