package com.example.webapp.web;

import com.example.webapp.service.CsvFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Slf4j
@RestController
@RequestMapping("file")
public class CsvFileController {

    @Autowired
    private CsvFileService csvFileService;

    @PostMapping("upload")
    public void upload(@RequestParam("file") MultipartFile file) {
        csvFileService.saveFileData(file);
    }

}
