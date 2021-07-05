package com.example.webapp.web;

import com.example.webapp.service.SseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


@Slf4j
@RestController
@RequestMapping("sse")
public class SseController {

    @Autowired
    private SseService sseService;

    @GetMapping(path = "subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(String id) {
        return sseService.createSseClient(id);
    }

    @GetMapping("unsubscribe")
    public void unSubscribe(String id) {
        sseService.completeSseClient(id);
    }
}
