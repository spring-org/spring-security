package com.example.securitybasicflow.application.controller;

import com.example.securitybasicflow.application.dto.MemberDto;
import com.example.securitybasicflow.application.dto.ReqSaveMember;
import com.example.securitybasicflow.application.dto.ReqUpdateMember;
import com.example.securitybasicflow.application.dto.ResMember;
import com.example.securitybasicflow.application.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberApi {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<ResMember> addMember(@RequestBody ReqSaveMember member) {

        MemberDto memberDto = memberService.saveMember(MemberDto.saveToDto(member));

        return ResponseEntity.status(HttpStatus.CREATED).body(ResMember.toRes(memberDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResMember> updateMember(@PathVariable Long id, @RequestBody ReqUpdateMember member) {

        MemberDto memberDto = memberService.updateMember(id, MemberDto.updateToDto(member));

        return ResponseEntity.status(HttpStatus.OK).body(ResMember.toRes(memberDto));
    }
}
