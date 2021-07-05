package com.example.webapp.service;

import com.example.webapp.entity.Member;
import com.example.webapp.repo.MemberRepository;
import com.example.webapp.web.model.SseDataModel;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    private final int BATCH_SIZE = 10000;

    @Transactional
    public List<Member> saveAll(List<Member> memberList) {
        List<Member> savedMember = Lists.newArrayList();

        int totalSize = memberList.size();
        AtomicInteger processCounter = new AtomicInteger();

        List<Member> temp = Lists.newArrayList();
        memberList.forEach(member -> {
            if (member.getId() != null) {
                temp.add(member);
            }

            processCounter.getAndIncrement();
            if (temp.size() == BATCH_SIZE) {
                savedMember.addAll(memberRepository.saveAll(temp));
                publishNotificationEvent(totalSize, processCounter.get());
                temp.clear();
            }
        });

        if (temp.size() > 0) {
            savedMember.addAll(memberRepository.saveAll(temp));
        }

        publishNotificationEvent(totalSize, processCounter.get());

        log.info("[Info] finished to insert member data.");

        return savedMember;
    }

    private void publishNotificationEvent(int totalDataCount, int savedDataCount) {
        SseDataModel sseDataModel = SseDataModel.builder()
                .totalCount(totalDataCount)
                .progressCount(savedDataCount)
                .build();
        eventPublisher.publishEvent(sseDataModel);
    }
}
