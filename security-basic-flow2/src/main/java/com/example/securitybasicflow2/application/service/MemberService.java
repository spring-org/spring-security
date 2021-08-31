package com.example.securitybasicflow2.application.service;

import com.example.securitybasicflow2.application.domain.MemberEntity;
import com.example.securitybasicflow2.application.dto.RequestSaveMember;
import com.example.securitybasicflow2.application.dto.RequestUpdateMember;

import java.util.List;

public interface MemberService {
    MemberEntity saveMember(RequestSaveMember saveMember);

    List<MemberEntity> getMembers();

    MemberEntity updateMember(Long memberId, RequestUpdateMember updateMember);

    boolean deleteMember(Long id);
}
