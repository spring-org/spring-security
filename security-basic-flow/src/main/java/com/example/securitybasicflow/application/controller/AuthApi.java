package com.example.securitybasicflow.application.controller;

import com.example.securitybasicflow.application.dto.ReqLoginMember;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthApi {

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody ReqLoginMember member) {
        return ResponseEntity.ok().body(member.getEmail());
    }
}
