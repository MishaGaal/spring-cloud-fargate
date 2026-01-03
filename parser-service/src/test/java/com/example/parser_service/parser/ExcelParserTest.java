package com.example.parser_service.parser;

import com.example.parser_service.model.Entry;
import com.example.parser_service.parcer.ExcelParser;
import com.example.parser_service.service.SqsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExcelParserTest {

    @Mock
    private SqsService sqsService;

    @InjectMocks
    private ExcelParser excelParser;

    private static final int MESSAGE_SIZE = 100;

    @Test
    void testParseSendsBatchedEntriesToSqs() throws Exception {

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("test-data.xlsx");
        assertNotNull(inputStream, "Test Excel file should exist in resources");

        excelParser.parseAndSend(inputStream);


        @SuppressWarnings("unchecked")
        ArgumentCaptor<List<Entry>> captor = ArgumentCaptor.forClass((Class) List.class);
        verify(sqsService, atLeastOnce()).sendBatch(captor.capture(), any(UUID.class));
        List<List<Entry>> sentBatches = captor.getAllValues();

        for (int i = 0; i < sentBatches.size() - 1; i++) {
            assertEquals(MESSAGE_SIZE, sentBatches.get(i).size(), "Intermediate batches should be full");
        }

        assertTrue(sentBatches.get(sentBatches.size() - 1).size() <= MESSAGE_SIZE, "Last batch can be less than MESSAGE_SIZE");
    }
}
