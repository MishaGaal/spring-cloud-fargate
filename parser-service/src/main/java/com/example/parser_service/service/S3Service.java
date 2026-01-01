package com.example.parser_service.service;

import com.example.parser_service.error.S3Error;
import io.awspring.cloud.s3.S3Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@Service
public class S3Service {

    private final S3Template s3Template;
    private final String bucket;
    private final String defaultKey = UUID.randomUUID().toString();

    public S3Service(S3Template s3Template, @Value("${app.s3.bucket}") String bucket) {
        this.s3Template = s3Template;
        this.bucket = bucket;
    }

    /**
     * Downloads a file from S3 as InputStream.
     */
    public InputStream downloadFile(String key) {
        key = validateFileName(key);
        log.info("Downloading {} from S3 bucket {}", key, bucket);
        try {
            return s3Template.download(bucket, key).getInputStream();
        } catch (Exception e) {
            log.error("Error while downloading {} from S3: {}", key, e.getMessage(), e);
            throw new S3Error("Failed to download file from S3", e);
        }
    }

    /**
     * Uploads a file to S3 from MultipartFile. Returns the generated S3 key.
     */
    public String uploadFile(String fileName, MultipartFile data) {
        String key = UUID.randomUUID() + "-" + validateFileName(fileName);
        try (InputStream inputStream = data.getInputStream()) {
            s3Template.upload(bucket, key, inputStream);
            log.info("Uploaded file {} to S3 bucket {}", key, bucket);
            return key;
        } catch (IOException e) {
            log.error("Failed to read file for upload", e);
            throw new IllegalArgumentException(e);
        } catch (Exception e) {
            log.error("Error while uploading {} to S3: {}", key, e.getMessage(), e);
            throw new S3Error("Failed to upload file to S3", e);
        }
    }

    private String validateFileName(String key) {
        return StringUtils.isEmpty(key) ? defaultKey : key;
    }
}
