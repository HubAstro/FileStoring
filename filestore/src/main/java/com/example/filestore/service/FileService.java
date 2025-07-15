package com.example.filestore.service;

import com.example.filestore.model.FileMetadata;
import com.example.filestore.model.User;
import com.example.filestore.repository.FileMetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;


import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class FileService {

    @Value("${upload.dir}")
    private String uploadDir;

    @Autowired private FileMetadataRepository fileRepo;

    public void storeFile(String name, MultipartFile file, User user) throws IOException {
        String userFolder = uploadDir + File.separator + user.getUsername();
        File dir = new File(userFolder);
        if (!dir.exists()) dir.mkdirs();

        File dest = new File(dir, file.getOriginalFilename());
        file.transferTo(dest);

        FileMetadata metadata = new FileMetadata();
        metadata.setFilename(file.getOriginalFilename());
        metadata.setName(name);
        metadata.setPath(dest.getAbsolutePath());
        metadata.setSize(file.getSize());
        metadata.setUploadedAt(LocalDateTime.now());
        metadata.setUser(user);

        fileRepo.save(metadata);
    }

    public Page<FileMetadata> getFilesByUser(User user, int page) {
        return fileRepo.findByUser(user, PageRequest.of(page, 10));
    }

    public void deleteFile(Long id, User user) {
        FileMetadata file = fileRepo.findByIdAndUser(id, user);
        if (file != null) {
            new File(file.getPath()).delete();
            fileRepo.delete(file);
        }
    }
    public Resource loadFile(Long id, User user) throws IOException {
        FileMetadata file = fileRepo.findByIdAndUser(id, user);
        if (file == null) {
            throw new IOException("File not found or access denied.");
        }

        File f = new File(file.getPath());
        if (!f.exists()) {
            throw new IOException("File missing on server.");
        }

        return new UrlResource(f.toURI());
    }
}




/**package com.example.filestore.service;

import com.example.filestore.model.FileMetadata;
import com.example.filestore.model.User;
import com.example.filestore.repository.FileMetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FileService {

    @Value("${upload.dir}")
    private String uploadDir;

    @Autowired
    private FileMetadataRepository fileRepo;

    public void storeFile(MultipartFile file, User user) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("Cannot upload empty file");
        }

        // Always use absolute path
        Path basePath = new File(uploadDir).getAbsoluteFile().toPath();

        // User-specific subdirectory
        Path userPath = basePath.resolve(user.getUsername());
        if (!Files.exists(userPath)) {
            Files.createDirectories(userPath);
        }

        // Unique + safe filename
        String originalFilename = file.getOriginalFilename();
        String safeFilename = System.currentTimeMillis() + "_" + originalFilename.replaceAll("[^a-zA-Z0-9\\.\\-_]", "_");

        Path destination = userPath.resolve(safeFilename);

        // Save the file
        file.transferTo(destination.toFile());

        // Save metadata
        FileMetadata metadata = new FileMetadata();
        metadata.setFilename(safeFilename);
        metadata.setPath(destination.toAbsolutePath().toString());
        metadata.setSize(file.getSize());
        metadata.setUploadedAt(LocalDateTime.now());
        metadata.setUser(user);

        fileRepo.save(metadata);
    }

    public List<FileMetadata> getFilesByUser(User user) {
        return fileRepo.findByUser(user);
    }
}**/
