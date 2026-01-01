package com.example.parser_service.service;

import com.example.parser_service.error.SqsSerializationException;
import com.example.parser_service.model.Entry;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class SqsService {

    private final QueueMessagingTemplate messagingTemplate;
    private final String queueName;

    public SqsService(QueueMessagingTemplate messagingTemplate,
                      @Value("${app.sqs.queue-url}") String queueName) {
        this.messagingTemplate = messagingTemplate;
        this.queueName = queueName;
    }


    public void sendBatch(List<Entry> entries, UUID extractionId) {
        try {
            Message<List<Entry>> message = MessageBuilder
                    .withPayload(entries)
                    .setHeader("extraction", extractionId).
                    build();
            messagingTemplate.convertAndSend(queueName, message);
            log.info("Sent batch of {} entries to SQS queue {}", entries.size(), queueName);
        } catch (Exception e) {
            log.error("Error sending SQS message batch: {}", e.getMessage(), e);
            throw new SqsSerializationException("Failed to serialize Entries", e);
        }
    }
}


