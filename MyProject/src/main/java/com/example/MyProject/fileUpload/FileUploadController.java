package com.example.MyProject.fileUpload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/file")
public class FileUploadController {
    @Autowired
    FileUploadService fileUploadService;

    @PostMapping
    public void uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        fileUploadService.uploadFile(file);
    }

    @GetMapping(value = "/{fileName}")
    @ResponseBody
    public final ResponseEntity<InputStreamResource> getFileByName(@PathVariable("fileName") String fileName) throws Exception {

        String folderPath = "C:\\Users\\emreo\\Projects\\MySpringProject\\MyProject\\uploadedFilesStorage\\"; //TODO: start path from current directory

        File initialFile = new File(folderPath+fileName);
        InputStream targetStream = new FileInputStream(initialFile);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("video/mp4"));
        headers.set("Accept-Ranges", "bytes");
        headers.set("Expires", "0");
        headers.set("Cache-Control", "no-cache, no-store");
        headers.set("Connection", "keep-alive");
        headers.set("Content-Transfer-Encoding", "binary");

        return new ResponseEntity<>(new InputStreamResource(targetStream), headers, HttpStatus.OK);
    }

    @GetMapping(path="/all")
    public void getVideos() {
        
    }

}
