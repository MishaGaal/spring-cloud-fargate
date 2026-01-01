package com.example.parser_service.controller;

import com.example.parser_service.error.S3Error;
import com.example.parser_service.error.SqsSerializationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(S3Error.class)
    public ResponseEntity<String> handleS3Error(S3Error ex) {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(ex.getMessage());
    }

    @ExceptionHandler(SqsSerializationException.class)
    public ResponseEntity<String> handleSqsSerializationException(
            SqsSerializationException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(ex.getMessage());
    }
}