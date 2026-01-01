package com.example.processor_service.service;

import com.example.processor_service.entity.CarEntity;
import com.example.processor_service.repository.CarRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageProcessorServiceTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private MessageProcessorService processorService;

    @Test
    void saveEntities_validJson_savesAll() throws Exception {
        UUID extractionId = UUID.randomUUID();
        String messageBody = String.format("[{\"extractionId\":\"%s\"}]", extractionId);

        Message<String> message = MessageBuilder.withPayload(messageBody)
                .setHeader("extraction", extractionId.toString())
                .build();

        CarEntity entity = new CarEntity();
        entity.setExtractionId(extractionId);

        when(objectMapper.readValue(eq(messageBody), ArgumentMatchers.<TypeReference<List<CarEntity>>>any()))
                .thenReturn(List.of(entity));


        processorService.saveEntities(message);

        verify(carRepository, times(1)).saveAll(List.of(entity));
    }

    @Test
    void saveEntities_invalidJson_logsError() throws Exception {
        String invalidJson = "{invalid_json}";

        Message<String> message = MessageBuilder.withPayload(invalidJson)
                .setHeader("extraction", "test-id")
                .build();

        when(objectMapper.readValue(eq(invalidJson), ArgumentMatchers.<TypeReference<List<CarEntity>>>any()))
                .thenThrow(new JsonProcessingException("bad json") {});

        processorService.saveEntities(message);

        verify(carRepository, never()).saveAll(any());
    }
}
