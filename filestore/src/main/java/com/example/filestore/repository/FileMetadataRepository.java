package com.example.filestore.repository;

import com.example.filestore.model.FileMetadata;
import com.example.filestore.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long> {
    Page<FileMetadata> findByUser(User user, Pageable pageable);
    FileMetadata findByIdAndUser(Long id, User user);
}
