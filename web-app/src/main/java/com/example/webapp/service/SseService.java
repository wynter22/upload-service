package com.example.webapp.service;

import com.example.webapp.web.model.SseDataModel;
import com.example.webapp.web.model.SseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class SseService {

    private static final Map<String, SseEmitter> CLIENTS = new ConcurrentHashMap<>();

    public SseEmitter createSseClient(String id) {
        SseEmitter emitter = new SseEmitter(120000L);

        CLIENTS.put(id, emitter);
        log.info("[Info] sseClient(id={}) is created.", id);

        emitter.onTimeout(() -> {
            CLIENTS.get(id).complete();
            CLIENTS.remove(id);
        });
        emitter.onCompletion(() -> {
            CLIENTS.remove(id);
            log.info("removed sseClient(id={})", id);
        });

        return emitter;
    }

    public SseEmitter getSseClient(String id) {
        if (CLIENTS.containsKey(id)) {
            return CLIENTS.get(id);
        }
        return null;
    }

    public void completeSseClient(String id) {
        SseEmitter sseEmitter = getSseClient(id);
        if (sseEmitter != null) {
            sseEmitter.complete();
            log.info("completed sseClient(id={})", id);
        } else {
            log.warn("sseClient(id={}) is not alive.", id);
        }
    }

    @EventListener
    void onNotification(SseDataModel messageModel) {
        List<String> deadEmitters = new ArrayList<>();
        log.info("on notification message. {}", messageModel);
        CLIENTS.forEach((id, emitter) -> {
            SseResponse response = SseResponse.builder()
                    .clientId(id)
                    .data(messageModel)
                    .build();
            try {
                emitter.send(response, MediaType.APPLICATION_JSON);
                log.info("sseClient(id={}) sent a message.", id);
            } catch (Exception e) {
                deadEmitters.add(id);
                log.info("dead sseClient(id={})", id);
            }
        });
        deadEmitters.forEach((clientId) -> CLIENTS.get(clientId).complete());
    }
}
