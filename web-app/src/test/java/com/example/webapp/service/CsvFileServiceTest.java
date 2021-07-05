package com.example.webapp.service;

import com.example.webapp.entity.Member;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class CsvFileServiceTest {

    @InjectMocks
    CsvFileService csvFileService;

    @Mock
    MemberService memberService;

    static final String CSV_FILE_NAME = "kakaopay_task_file.csv";

    static MockMultipartFile multipartFile;

    @BeforeEach
    void init() throws IOException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(classLoader.getResource(CSV_FILE_NAME).getFile());
        FileInputStream inputStream = new FileInputStream(file);

        multipartFile = new MockMultipartFile(
                CSV_FILE_NAME,
                CSV_FILE_NAME,
                MediaType.MULTIPART_FORM_DATA_VALUE,
                inputStream);
    }

    @Test
    @DisplayName("파일을 파싱하고 파싱한 데이터를 DB에 정상적으로 저장한다.")
    void save_file_data() {
        csvFileService.saveFileData(multipartFile);

        verify(memberService).saveAll(any(List.class));
    }

    @Test
    @DisplayName("파일을 파싱하고 파싱한 데이터를 반환한다.")
    void convert_csv_file_to_data() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // given
        // private method test
        Method method = csvFileService
                .getClass()
                .getDeclaredMethod("convertCsvFileToData", MultipartFile.class);

        method.setAccessible(true);

        // when
        List<Member> memberList = (List<Member>) method.invoke(csvFileService, multipartFile);

        // then
        assertTrue(memberList != null);
    }

}
