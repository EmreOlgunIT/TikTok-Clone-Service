package com.example.MyProject.fileUpload;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileUploadService {

    public void uploadFile(MultipartFile file) throws IOException {
        file.transferTo(new File("C:\\Users\\emreo\\Projects\\MySpringProject\\MyProject\\uploadedFilesStorage\\"+file.getOriginalFilename()));
    }
}
