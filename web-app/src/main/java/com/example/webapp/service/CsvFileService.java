package com.example.webapp.service;


import com.example.webapp.entity.Member;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Slf4j
@Service
public class CsvFileService {

    @Autowired
    private MemberService memberService;

    public void saveFileData(MultipartFile csvFile) {
        List<Member> memberList = convertCsvFileToData(csvFile);
        if (memberList != null) {
            memberService.saveAll(memberList);
        }
    }

    private List<Member> convertCsvFileToData(MultipartFile multipartFile) {
        try {
            InputStreamReader stream = new InputStreamReader(multipartFile.getInputStream());
            CSVReader reader = new CSVReader(stream);

            return (List<Member>) new CsvToBeanBuilder(reader)
                    .withType(Member.class)
                    .build()
                    .parse();

        } catch (IllegalStateException illegalStateException) {
            log.error("[ERROR] failed to parse csv file to member data.");
            illegalStateException.printStackTrace();
        } catch (IOException ex) {
            log.error("[ERROR] failed to get multipart file input stream!!");
            ex.printStackTrace();
        }
        return null;
    }
}
