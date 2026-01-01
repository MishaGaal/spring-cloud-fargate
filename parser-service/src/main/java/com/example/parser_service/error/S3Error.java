package com.example.parser_service.error;

import com.amazonaws.SdkClientException;

public class S3Error extends RuntimeException {

    public S3Error(String message) {
        super(message);
    }

    public S3Error(String awsClientError, Exception e) {
        super(String.format("%s: %s", awsClientError, e ));
    }
}
