package ru.netology.backend.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.netology.backend.controller.UserFile;
import ru.netology.backend.exception.BadRequest;
import ru.netology.backend.exception.InternalServerError;
import ru.netology.backend.loger.Loger;
import ru.netology.backend.model.User;

import java.util.List;

@Repository
public class FileRepository {
    final private JdbcTemplate jdbcTemplate;

    public FileRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addFile(String fileName, long userId) {
        try {
            String sql = "insert into FILES(name, files_id) values (?,?)";
            jdbcTemplate.update(sql, fileName, userId);
            Loger.write("INFO", "Фаил успешно загружен " + fileName);
        } catch (EmptyResultDataAccessException e) {
            throw  new BadRequest("Error input data");
        }
    }

    public List<UserFile> getList(long userId , int limit) {
        try {
            String sql = "SELECT name from Files where files_id = ? LIMIT ?";
            var files = jdbcTemplate.query(sql, new Object[]{userId, limit}, (rs, rowNum) ->
                    new UserFile(
                            rs.getString("name")
                    ));
            Loger.write("INFO", "Успешно получен список файлов для пользователя " + userId);
            return files;
        } catch (EmptyResultDataAccessException e) {
            throw  new InternalServerError("Error getting file list");
        }
    }

    public void editName(String filename, String ediFilename) {
        try {
            this.jdbcTemplate.update(
                    "update files set name = ? where name = ?",
                    ediFilename, filename);
            Loger.write("INFO", "Фаил " + filename + "успешно перименован  на " + ediFilename);

        } catch (EmptyResultDataAccessException e) {
            throw  new InternalServerError("Error upload file");        }
    }

    public void deleteFile(String filename) {
        try {
            this.jdbcTemplate.update (
                    "DELETE FROM files WHERE name = ?",
                    filename);
            Loger.write("INFO", "Фаил успешно удален " + filename);
        } catch (EmptyResultDataAccessException e) {
            throw new InternalServerError("Error delet file");
        }
    }
}

