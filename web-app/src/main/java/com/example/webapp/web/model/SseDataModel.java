package com.example.webapp.web.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SseDataModel {

    //전체 Row
    private int totalCount;

    //DB에 저장된 Row
    private int progressCount;

}
