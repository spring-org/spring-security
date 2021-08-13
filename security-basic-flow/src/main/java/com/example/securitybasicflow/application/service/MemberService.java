package com.example.securitybasicflow.application.service;

import com.example.securitybasicflow.application.dto.MemberDto;

public interface MemberService {

    MemberDto saveMember(MemberDto memberDto);

    MemberDto updateMember(Long id, MemberDto updateToDto);
}
