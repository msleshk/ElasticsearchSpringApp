package com.example.ElasticsearchSpringApp.controller;

import com.example.ElasticsearchSpringApp.service.DataLoadService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/load")
public class DataLoadController {
    private final DataLoadService dataLoadService;

    public DataLoadController(DataLoadService dataLoadService) {
        this.dataLoadService = dataLoadService;
    }

    @PostMapping("/start")
    public String loadData() throws IOException {
        try {
            dataLoadService.loadDataToElasticsearch();
            return "Data loaded successfully";
        } catch (Exception e) {
            return "failed to load data" + e.getMessage();
        }
    }
}
