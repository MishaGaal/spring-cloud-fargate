package com.example.parser_service.service;

import com.amazonaws.util.IOUtils;
import com.example.parser_service.parcer.ExcelParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExcelServiceTest {

    @Mock
    private ExcelParser parser;

    @Mock
    private S3Service s3Service;

    @InjectMocks
    private ExcelService excelService;

    private final String fileName = "test-data.xlsx";


    @Test
    void parseAndSendEntitiesFromS3_success() throws Exception {

        byte[] data = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("test-data.xlsx"));
        assertNotNull(data, "Test Excel file should exist in resources");
        InputStream s3InputStream = new ByteArrayInputStream(data);

        when(s3Service.downloadFile(fileName)).thenReturn(s3InputStream);

        excelService.parseAndSendEntitiesFromS3(fileName);

        verify(s3Service, times(1)).downloadFile(fileName);
        verify(parser, times(1)).parseAndSend(s3InputStream);
    }

    @Test
    void parseAndSendEntitiesFromS3_s3ThrowsException_logsError() throws Exception {
        when(s3Service.downloadFile(fileName)).thenThrow(new RuntimeException("S3 error"));

        excelService.parseAndSendEntitiesFromS3(fileName);

        verify(parser, never()).parseAndSend(any());
    }
}

