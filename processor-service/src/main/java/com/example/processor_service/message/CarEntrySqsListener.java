package com.example.processor_service.message;

import com.example.processor_service.service.MessageProcessorService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CarEntrySqsListener {

    private final MessageProcessorService messageProcessorService;

    @SqsListener(value = "${app.sqs.queue-url}")
    public void receiveMessage(Message<String> message) {
        messageProcessorService.saveEntities(message);
    }
}
