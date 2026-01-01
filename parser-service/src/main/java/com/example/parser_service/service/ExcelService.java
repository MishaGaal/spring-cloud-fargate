package com.example.parser_service.service;

import com.example.parser_service.parcer.ExcelParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Slf4j
@Service
public class ExcelService {

    private final ExcelParser parser;
    private final S3Service s3Service;

    public ExcelService(ExcelParser parser, S3Service s3Service) {
        this.parser = parser;
        this.s3Service = s3Service;
    }

    public void parseAndSendEntitiesFromS3(String fileName) {
        try (InputStream stream = s3Service.downloadFile(fileName)) {
            parser.parseAndSend(stream);
        } catch (Exception e) {
            log.error("Failed to process file: {}", fileName, e);
        }
    }
}
