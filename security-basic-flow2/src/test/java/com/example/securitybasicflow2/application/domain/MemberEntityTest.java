package com.example.securitybasicflow2.application.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberEntityTest {

    @DisplayName("엔티티 생성 테스트")
    @Test
    void testCase1() {
        MemberEntity actual = MemberEntity.builder()
                .name("seok")
                .email("seok@gmail.com")
                .password("1234")
                .build();

        MemberEntity expected = MemberEntity.builder()
                .name("seok")
                .email("seok@gmail.com")
                .password("1234")
                .build();
        assertThat(actual).isEqualTo(expected);
    }
}