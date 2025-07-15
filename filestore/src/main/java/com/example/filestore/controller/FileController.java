package com.example.filestore.controller;

import com.example.filestore.model.FileMetadata;
import com.example.filestore.model.User;
import com.example.filestore.service.FileService;
import com.example.filestore.service.UserService;

import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;



@Controller
public class FileController {

    @Autowired private FileService fileService;
    @Autowired private UserService userService;

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("name") String name,
                                   @RequestParam("file") MultipartFile file,
                                   Authentication auth,
                                   HttpSession session) {
        try {
            User user = userService.findByUsername(auth.getName());
            fileService.storeFile(name, file, user);
            return "redirect:/dashboard";
        } catch (Exception e) {
            session.setAttribute("error", "File upload failed: " + e.getMessage());
            return "redirect:/dashboard";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model,
                            Authentication auth,
                            @RequestParam(defaultValue = "0") int page) {
        User user = userService.findByUsername(auth.getName());
        Page<FileMetadata> files = fileService.getFilesByUser(user, page);

        model.addAttribute("files", files.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", files.getTotalPages());
        return "dashboard";
    }
    
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id, Authentication auth) throws IOException {
        User user = userService.findByUsername(auth.getName());
        Resource file = fileService.loadFile(id, user);

        FileMetadata metadata = fileService.getFilesByUser(user, 0)
                .stream().filter(f -> f.getId().equals(id)).findFirst().orElse(null);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + (metadata != null ? metadata.getFilename() : "file") + "\"")
                .body(file);
    }

    @PostMapping("/delete")
    public String deleteFile(@RequestParam("id") Long id, Authentication auth) {
        User user = userService.findByUsername(auth.getName());
        fileService.deleteFile(id, user);
        return "redirect:/dashboard";
    }
}
