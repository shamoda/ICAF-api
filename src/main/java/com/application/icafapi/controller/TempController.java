package com.application.icafapi.controller;

import com.application.icafapi.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "*")
public class TempController {

    @Autowired
    private FileService service;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        service.uploadFile(file, "TestFile", "presentation");
        return "File Uploaded Successfully";
    }

    @GetMapping("/download/{file}")
    public ResponseEntity<ByteArrayResource> download(@PathVariable String file) {
        byte[] data = service.downloadFile(file, "presentation");
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok().contentLength(data.length).header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\""+file+"\"").body(resource);
    }

    @DeleteMapping("/delete/{file}")
    public void delete(@PathVariable String file) {
        service.deleteFile(file, "paper");
    }

    @GetMapping("/filenames/{type}")
    public ResponseEntity<?> getFileNames(@PathVariable String type) {
        return new ResponseEntity<>(service.getFileNames(type), HttpStatus.OK);
    }

}
