package com.example.webapp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
public class SseServiceTest {

    @InjectMocks
    SseService sseService;

    static String CLIENT_ID;

    @BeforeEach
    void init() {
        CLIENT_ID = UUID.randomUUID().toString();
    }

    @Test
    @DisplayName("sse client를 정상적으로 생성한다.")
    void create_sse_client() {
        SseEmitter sseEmitter = sseService.createSseClient(CLIENT_ID);
        assertNotNull(sseEmitter);
    }

    @Test
    @DisplayName("sse client를 정상적으로 제거한다.")
    void destroy_sse_client() {
        sseService.completeSseClient(CLIENT_ID);
        assertNull(sseService.getSseClient(CLIENT_ID));
    }

}
