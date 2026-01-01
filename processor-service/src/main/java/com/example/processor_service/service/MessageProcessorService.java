package com.example.processor_service.service;

import com.example.processor_service.entity.CarEntity;
import com.example.processor_service.repository.CarRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class MessageProcessorService {

    private final CarRepository carRepository;
    private final ObjectMapper objectMapper;


    public void saveEntities(Message<String> message) {
        List<CarEntity> entities = parseMessage(message);

        if (entities.isEmpty()) {
            log.warn("Received empty entity list, extractionId={}", getExtractionId(message));
            return;
        }

        String extractionId = getExtractionId(message);
        log.info("Saving {} entities for extractionId={}", entities.size(), extractionId);

        try {
            carRepository.saveAll(entities);
        } catch (Exception e) {
            log.error("Failed to save entities for extractionId={}", extractionId, e);
        }
    }

    private List<CarEntity> parseMessage(Message<String> message) {
        try {
            return objectMapper.readValue(
                    message.getPayload(),
                    new TypeReference<>() {}
            );
        } catch (JsonProcessingException e) {
            log.error("Failed to parse entities from message, extractionId={}", getExtractionId(message), e);
            return Collections.emptyList();
        }
    }

    private String getExtractionId(Message<String> message) {
        Object header = message.getHeaders().get("extraction");
        return header != null ? header.toString() : "unknown";
    }
}

