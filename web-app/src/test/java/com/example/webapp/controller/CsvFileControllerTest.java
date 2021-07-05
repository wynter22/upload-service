package com.example.webapp.controller;


import com.example.webapp.service.CsvFileService;
import com.example.webapp.web.CsvFileController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.io.File;
import java.io.FileInputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CsvFileController.class)
public class CsvFileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CsvFileService csvFileService;

    private static final String CSV_FILE_NAME = "kakaopay_task_file.csv";

    @Test
    @DisplayName("파일 업로드 api 정상 호출.")
    void file_upload() throws Exception {
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(classLoader.getResource(CSV_FILE_NAME).getFile());
        FileInputStream inputStream = new FileInputStream(file);

        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                CSV_FILE_NAME,
                MediaType.MULTIPART_FORM_DATA_VALUE,
                inputStream);

        mockMvc.perform(multipart("/file/upload")
                .file(multipartFile))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }


}
