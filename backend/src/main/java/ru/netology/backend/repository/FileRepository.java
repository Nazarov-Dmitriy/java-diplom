package ru.netology.backend.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.netology.backend.model.FilesModel;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FilesModel, Long> {
    @Query(value = "SELECT * from Files where files_id = ?1 LIMIT ?2", nativeQuery = true)
    List<FilesModel> getList(long userId, int limit);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update files set name = ?2 where name = ?1", nativeQuery = true)
    void editName(String filename, String ediFilename);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM files WHERE name = ?1", nativeQuery = true)
    void deleteFile(String filename);
}