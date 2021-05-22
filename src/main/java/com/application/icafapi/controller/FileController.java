package com.application.icafapi.controller;

import com.application.icafapi.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class FileController {

    private final FileService service;

    @Autowired
    public FileController(FileService service) {
        this.service = service;
    }

    @GetMapping("/download/{type}/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String type, @PathVariable String fileName) {
        byte[] data = service.downloadFile(fileName, type);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok().contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\""+fileName+"\"").body(resource);
    }
}
