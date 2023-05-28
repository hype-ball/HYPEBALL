package com.project.hypeball.repository;

import com.project.hypeball.domain.AttachedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FileRepository extends JpaRepository<AttachedFile, Long> {

    @Query("select f.storeFileName, r.id from AttachedFile f join Review r on f.review.id = r.id where r.store.id = :storeId")
    List<AttachedFile> findBystoreId(@Param("storeId") Long storeId);

}
