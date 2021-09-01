package com.example.securitybasicflow2.application.service;

import com.example.securitybasicflow2.application.domain.MemberEntity;
import com.example.securitybasicflow2.application.dto.RequestSaveMember;
import com.example.securitybasicflow2.application.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        this.memberService = new MemberServiceImpl(memberRepository);
    }

    @ParameterizedTest(name = "Member = email: {0}, name: {1}, password: {2} :: 입력 테스트")
    @CsvSource(value = {"seok@gmail.com, seok, 1234"})
    void when_saveMember_expect_memberRepository_save_call(String email, String name, String password) {

        RequestSaveMember build = createSaveMember(email, name, password);
        MemberEntity member = createEntityMember(email, name, password);

        memberService.saveMember(build);

        then(memberRepository).should(times(1)).save(member);
    }

    private MemberEntity createEntityMember(String email, String name, String password) {
        return MemberEntity.builder()
                .email(email)
                .name(name)
                .password(password)
                .build();
    }

    private RequestSaveMember createSaveMember(String email, String name, String password) {
        return RequestSaveMember.builder()
                .email(email)
                .name(name)
                .password(password)
                .build();
    }
}