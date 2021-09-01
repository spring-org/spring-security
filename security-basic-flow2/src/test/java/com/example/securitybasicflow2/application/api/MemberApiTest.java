package com.example.securitybasicflow2.application.api;

import com.example.securitybasicflow2.application.domain.MemberEntity;
import com.example.securitybasicflow2.application.dto.RequestSaveMember;
import com.example.securitybasicflow2.application.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(value = MemberApi.class)
class MemberApiTest {

    @MockBean
    private MemberService memberService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("사용자 정상 등록 테스트")
    @Test
    void when_post_member_expect_valid_response() throws Exception {
        when(memberService.saveMember(any(RequestSaveMember.class)))
                .thenReturn(createMemberEntity());

        mockMvc.perform(post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createSaveMember()))
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").exists())
                .andExpect(jsonPath("$.password").exists())
                .andExpect(jsonPath("$.name").exists());
    }

    private RequestSaveMember createSaveMember() {
        return RequestSaveMember.builder()
                .email("email")
                .name("name")
                .password("password")
                .build();
    }

    private MemberEntity createMemberEntity() {
        return MemberEntity.builder()
                .email("email")
                .name("name")
                .password("password")
                .build();
    }
}