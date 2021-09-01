package com.example.securitybasicflow2.application.service;

import com.example.securitybasicflow2.application.domain.MemberEntity;
import com.example.securitybasicflow2.application.dto.RequestSaveMember;
import com.example.securitybasicflow2.application.dto.RequestUpdateMember;
import com.example.securitybasicflow2.application.exception.DuplicateMemberEmailException;
import com.example.securitybasicflow2.application.exception.MemberNotFoundException;
import com.example.securitybasicflow2.application.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public MemberEntity saveMember(RequestSaveMember saveMember) {
        if (memberRepository.existsByEmail(saveMember.getEmail())) {
            throw new DuplicateMemberEmailException(404, "동일한 이메일 정보가 존재합니다.");
        }

        return memberRepository.save(saveMember.toEntity());
    }

    public List<MemberEntity> getMembers() {
        return memberRepository.findAll();
    }

    public MemberEntity updateMember(Long memberId, RequestUpdateMember updateMember) {
        MemberEntity findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(404, "사용자가 존재하지 않습니다."));

        return findMember.update(updateMember);
    }

    public boolean deleteMember(Long id) {
        memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(404, "사용자가 존재하지 않습니다."));

        memberRepository.deleteById(id);

        return memberRepository.existsById(id);
    }
}
