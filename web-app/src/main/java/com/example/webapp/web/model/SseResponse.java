package com.example.webapp.web.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SseResponse {

    private String clientId;

    private SseDataModel data;
}
