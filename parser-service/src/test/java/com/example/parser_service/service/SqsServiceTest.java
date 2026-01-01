package com.example.parser_service.service;

import com.example.parser_service.error.SqsSerializationException;
import com.example.parser_service.model.Entry;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SqsServiceTest {

    @Mock
    private QueueMessagingTemplate messagingTemplate;

    @InjectMocks
    private SqsService sqsService;

    private final String queueName = "excel-entries";

    @BeforeEach
    void setUp() {
        sqsService = new SqsService(messagingTemplate, queueName);
    }

    @Test
    void sendEntry_success() {
        List<Entry> data = new ArrayList<>();

        sqsService.sendBatch(data, UUID.randomUUID());

        verify(messagingTemplate, times(1)).convertAndSend(queueName, data);
    }

    @Test
    void sendEntry_exception_throwsSqsSerializationException() {
        List<Entry> data = new ArrayList<>();


        doThrow(new RuntimeException("AWS error")).when(messagingTemplate).convertAndSend(queueName, data);

        SqsSerializationException exception = assertThrows(SqsSerializationException.class,
                () -> sqsService.sendBatch(data, UUID.randomUUID()));

        assertTrue(exception.getMessage().contains("Failed to serialize Entry"));

        verify(messagingTemplate, times(1)).convertAndSend(queueName, data);
    }
}
