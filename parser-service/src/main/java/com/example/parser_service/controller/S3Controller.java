package com.example.parser_service.controller;

import com.example.parser_service.validator.ExcelFileValidator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.example.parser_service.service.S3Service;

@RestController
@RequestMapping("/s3")
public class S3Controller {

    private final S3Service s3Service;

    public S3Controller(S3Service s3Service) {
        this.s3Service = s3Service;
    }


    @PostMapping("/upload")
    public String uploadExcel(@RequestParam("file") MultipartFile file) {
        ExcelFileValidator.validate(file);
        return s3Service.uploadFile(file.getOriginalFilename(), file);
    }
}
