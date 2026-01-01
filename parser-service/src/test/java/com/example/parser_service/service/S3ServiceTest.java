package com.example.parser_service.service;

import com.example.parser_service.error.S3Error;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class S3ServiceTest {

    @Mock
    private S3Template s3Template;

    @InjectMocks
    private S3Service s3Service;

    @BeforeEach
    void setUp() {
        s3Service = new S3Service(s3Template, "test-bucket");
    }

    @Test
    void downloadFile_success() throws Exception {
        String key = "test.xlsx";
        byte[] fakeData = "data".getBytes();
        InputStream fakeStream = new ByteArrayInputStream(fakeData);

        S3Resource resource = mock(S3Resource.class);
        when(resource.getInputStream()).thenReturn(fakeStream);
        when(s3Template.download("test-bucket", key)).thenReturn(resource);

        InputStream result = s3Service.downloadFile(key);

        assertNotNull(result);
        byte[] buffer = new byte[fakeData.length];
        assertEquals(fakeData.length, result.read(buffer));
        assertArrayEquals(fakeData, buffer);

        verify(s3Template).download("test-bucket", key);
    }

    @Test
    void downloadFile_s3Exception_throwsS3Error() {
        String key = "missing.xlsx";

        when(s3Template.download("test-bucket", key)).thenThrow(new RuntimeException("AWS error"));

        S3Error exception = assertThrows(S3Error.class, () -> s3Service.downloadFile(key));
        assertTrue(exception.getMessage().contains("Failed to download"));

        verify(s3Template).download("test-bucket", key);
    }

    @Test
    void uploadFile_success() throws Exception {
        String fileName = "data.xlsx";
        MockMultipartFile file = new MockMultipartFile(fileName, "content".getBytes());

        ArgumentCaptor<InputStream> inputCaptor = ArgumentCaptor.forClass(InputStream.class);
        ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);

        String returnedKey = s3Service.uploadFile(fileName, file);

        assertNotNull(returnedKey);
        assertTrue(returnedKey.contains(fileName));

        verify(s3Template).upload(eq("test-bucket"), keyCaptor.capture(), inputCaptor.capture());
        assertEquals(returnedKey, keyCaptor.getValue());
    }

    @Test
    void uploadFile_s3Exception_throwsS3Error() throws Exception {
        String fileName = "data.xlsx";
        MockMultipartFile file = new MockMultipartFile(fileName, "content".getBytes());

        doThrow(new RuntimeException("AWS error"))
                .when(s3Template).upload(anyString(), anyString(), any(InputStream.class));

        S3Error exception = assertThrows(S3Error.class, () -> s3Service.uploadFile(fileName, file));
        assertTrue(exception.getMessage().contains("Failed to upload"));

        verify(s3Template).upload(anyString(), anyString(), any(InputStream.class));
    }
}
