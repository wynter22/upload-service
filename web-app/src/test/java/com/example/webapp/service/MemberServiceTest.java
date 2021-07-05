package com.example.webapp.service;


import com.example.webapp.entity.Member;
import com.example.webapp.repo.MemberRepository;
import com.example.webapp.web.model.SseDataModel;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class MemberServiceTest {

    @InjectMocks
    private MemberService service;

    @Mock
    private MemberRepository mockMemberRepository;

    @Mock
    private ApplicationEventPublisher mockEventPublisher;

    private final int MEMBER_LIST_COUNTER = 100001;

    private final int BATCH_SIZE = 10000;

    @Test
    @DisplayName("데이터를 Batch Size씩 나눠 저장하고, 이벤트를 정상적으로 발생시킨다.")
    void save_member_list() {
        // given
        List<Member> memberList = Lists.newArrayList();
        while (MEMBER_LIST_COUNTER > memberList.size()) {
            Long id = memberList.size() + 1L;
            Member member = Member.builder()
                    .id(id)
                    .firstName(null)
                    .lastName(null)
                    .email(null)
                    .build();
            memberList.add(member);
        }

        // when
        service.saveAll(memberList);

        // then
        int runCounter = memberList.size() / BATCH_SIZE;
        if (memberList.size() % BATCH_SIZE > 0) {
            runCounter++;
        }
        verify(mockMemberRepository, times(runCounter)).saveAll(any(List.class));
        verify(mockEventPublisher, times(runCounter)).publishEvent(any(SseDataModel.class));
    }

    @Test
    @DisplayName("데이터가 없는 경우. 완료 이벤트를 1회 발생시킴.")
    void save_member_list_throw_error() {
        // given
        List<Member> memberList = Lists.newArrayList();
        // when
        service.saveAll(memberList);

        verify(mockMemberRepository, never()).saveAll(any(List.class));
        verify(mockEventPublisher, times(1)).publishEvent(any(SseDataModel.class));
    }

}
