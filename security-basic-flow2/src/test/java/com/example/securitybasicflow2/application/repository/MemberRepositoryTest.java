package com.example.securitybasicflow2.application.repository;

import com.example.securitybasicflow2.application.domain.MemberEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    private MemberEntity saveMember() {
        MemberEntity saveMember = MemberEntity.builder()
                .name("seok")
                .email("seok@gmail.com")
                .password("1234")
                .build();
        return saveMember;
    }

    @DisplayName("사용자 입력 테스트")
    @Test
    void testCase1() {

        MemberEntity saveMember = saveMember();

        MemberEntity save = memberRepository.save(saveMember);

        assertThat(save.getEmail()).isEqualTo("seok@gmail.com");
        assertThat(save.getRole()).isNull();
    }

    @DisplayName("사용자 권한 추가 테스트")
    @Test
    void testCase2() {
        MemberEntity saveMember = saveMember();

        MemberEntity savedMember = memberRepository.save(saveMember);
        savedMember.grantRole(MemberEntity.Role.ROLE_USER);

        // update
        memberRepository.flush();

        MemberEntity updatedMember = memberRepository.findByEmail(savedMember.getEmail())
                .orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다."));

        assertThat(updatedMember.getRole()).isEqualTo(MemberEntity.Role.ROLE_USER);
    }

    @DisplayName("사용자 삭제 테스트")
    @Test
    void testCase3() {
        MemberEntity saveMember = saveMember();

        MemberEntity savedMember = memberRepository.save(saveMember);

        memberRepository.delete(savedMember);

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(
                        () ->
                                memberRepository.findByEmail(savedMember.getEmail())
                                        .orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다."))
                );
    }
}