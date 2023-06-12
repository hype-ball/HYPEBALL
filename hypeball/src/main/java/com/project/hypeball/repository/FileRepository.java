package com.project.hypeball.repository;

import com.project.hypeball.domain.AttachedFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<AttachedFile, Long>, FileRepositoryInterface {


}
