package com.example.parser_service.error;

public class SqsSerializationException extends RuntimeException {
    public SqsSerializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
