package com.example.parser_service.controller;

import com.example.parser_service.service.ExcelService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/excel")
public class ExcelController {

    private final ExcelService service;

    public ExcelController(ExcelService service) {
        this.service = service;
    }


    @GetMapping("/parse")
    public void parseAndSendEntitiesFromS3(@RequestParam(name = "fileName", required = false)String fileName) {
        service.parseAndSendEntitiesFromS3(fileName);
    }

}