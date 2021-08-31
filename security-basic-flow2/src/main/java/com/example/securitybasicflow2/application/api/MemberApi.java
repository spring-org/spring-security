package com.example.securitybasicflow2.application.api;

import com.example.securitybasicflow2.application.domain.MemberEntity;
import com.example.securitybasicflow2.application.dto.RequestSaveMember;
import com.example.securitybasicflow2.application.dto.RequestUpdateMember;
import com.example.securitybasicflow2.application.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/members")
@RequiredArgsConstructor
public class MemberApi {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<?> getMembers() {

        List<MemberEntity> members = memberService.getMembers();

        return ResponseEntity.status(HttpStatus.OK).body(members);
    }

    @PostMapping
    public ResponseEntity<?> addMember(@Valid @RequestBody RequestSaveMember saveMember) {

        MemberEntity savedMember = memberService.saveMember(saveMember);

        return ResponseEntity.status(HttpStatus.OK).body(savedMember);
    }

    @PutMapping(value = "/{memberId}")
    public ResponseEntity<?> updateMember(
            @PathVariable("memberId") Long memberId, @Valid @RequestBody RequestUpdateMember updateMember) {

        MemberEntity updatedMember = memberService.updateMember(memberId, updateMember);

        return ResponseEntity.status(HttpStatus.OK).body(updatedMember);
    }

    @DeleteMapping(value = "/{memberId}")
    public ResponseEntity<Void> deleteMember(
            @PathVariable("memberId") Long memberId) {

        memberService.deleteMember(memberId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
