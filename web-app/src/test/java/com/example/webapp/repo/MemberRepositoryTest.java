package com.example.webapp.repo;

import com.example.webapp.entity.Member;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;


@DataJpaTest
@Transactional
public class MemberRepositoryTest {

    @Mock
    MemberRepository mockMemberRepository;

    @Test
    @Rollback
    @DisplayName("member list 정상 저장.")
    void save_all_mock_member_list() {

        List<Member> memberList = Lists.newArrayList();
        while (10 > memberList.size()) {
            Long id = memberList.size() + 1L;
            Member member = Member.builder()
                    .id(id)
                    .firstName(null)
                    .lastName(null)
                    .email(null)
                    .build();
            memberList.add(member);
        }

        mockMemberRepository.saveAll(memberList);
        verify(mockMemberRepository).saveAll(any(List.class));
    }

}
