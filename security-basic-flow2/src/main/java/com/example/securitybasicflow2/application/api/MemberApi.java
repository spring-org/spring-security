package com.example.securitybasicflow2.application.api;

import com.example.securitybasicflow2.application.domain.MemberEntity;
import com.example.securitybasicflow2.application.dto.RequestSaveMember;
import com.example.securitybasicflow2.application.dto.RequestUpdateMember;
import com.example.securitybasicflow2.application.dto.ResponseMember;
import com.example.securitybasicflow2.application.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/members")
@RequiredArgsConstructor
public class MemberApi {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<List<ResponseMember>> getMembers() {

        List<ResponseMember> members = memberService.getMembers().stream()
                .map(ResponseMember::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(members);
    }

    @PostMapping
    public ResponseEntity<ResponseMember> addMember(@Valid @RequestBody RequestSaveMember saveMember) {

        MemberEntity savedMember = memberService.saveMember(saveMember);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseMember.toResponse(savedMember));
    }

    @PutMapping(value = "/{memberId}")
    public ResponseEntity<ResponseMember> updateMember(
            @PathVariable("memberId") Long memberId, @Valid @RequestBody RequestUpdateMember updateMember) {

        MemberEntity updatedMember = memberService.updateMember(memberId, updateMember);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseMember.toResponse(updatedMember));
    }

    @DeleteMapping(value = "/{memberId}")
    public ResponseEntity<Void> deleteMember(
            @PathVariable("memberId") Long memberId) {

        memberService.deleteMember(memberId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
