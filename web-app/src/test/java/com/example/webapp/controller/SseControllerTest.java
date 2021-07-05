package com.example.webapp.controller;

import com.example.webapp.service.SseService;
import com.example.webapp.web.SseController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SseController.class)
public class SseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SseService sseService;

    static String CLIENT_ID;

    @BeforeEach
    void init() {
        CLIENT_ID = UUID.randomUUID().toString();
    }


    @Test
    @DisplayName("sse client 생성 api 정상 호출")
    void subscribe_sse_client() throws Exception {
        mockMvc.perform(get("/sse/subscribe", CLIENT_ID))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("sse client 제거 api 정상 호출")
    void unsubscribe_sse_client() throws Exception {
        mockMvc.perform(get("/sse/unsubscribe", CLIENT_ID))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

}
