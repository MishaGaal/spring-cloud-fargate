package com.example.parser_service.validator;

import com.example.parser_service.error.S3Error;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

public class ExcelFileValidator {

    public static void validate(MultipartFile file) {
        if (Objects.isNull(file) || file.isEmpty()) {
            throw new S3Error("Error uploading, file is missing");
        }
        if (!file.getOriginalFilename().endsWith(".xls") && !file.getOriginalFilename().endsWith(".xlsx")) {
            throw new IllegalArgumentException("Only Excel files are allowed");
        }
    }
}
