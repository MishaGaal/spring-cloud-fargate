package com.example.parser_service.parcer;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.example.parser_service.model.Entry;
import com.example.parser_service.service.SqsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ExcelParser {

    private static final int MESSAGE_SIZE = 100;


    private final SqsService sqsService;

    public ExcelParser(SqsService sqsService) {
        this.sqsService = sqsService;
    }

    public void parseAndSend(InputStream fileStream) {
        UUID extractionId = UUID.randomUUID();
        log.info("Starting extraction {}", extractionId);
        List<Entry> batch = new ArrayList<>();

        EasyExcel.read(fileStream, Entry.class, new AnalysisEventListener<Entry>() {
            @Override
            public void invoke(Entry entry, AnalysisContext context) {
               try{
                   entry.setExtractionId(extractionId);
                   entry.setExtractionDate(LocalDate.now());
                   batch.add(entry);

                   if (batch.size() >= MESSAGE_SIZE) {
                       sqsService.sendBatch(batch, extractionId);
                       batch.clear();
                   }
               } catch (Exception e) {
                   log.error("Couldn't send entry to sqs queue {}" , entry, e);
               }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                if (!batch.isEmpty()) {
                    try {
                        sqsService.sendBatch(batch, extractionId);
                        batch.clear();
                    } catch (Exception e) {
                        log.error("Couldn't send final batch to SQS", e);
                    }
                }
            }
        }).sheet().doRead();
    }
}
