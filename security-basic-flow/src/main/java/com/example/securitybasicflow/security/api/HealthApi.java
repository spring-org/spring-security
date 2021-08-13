package com.example.securitybasicflow.security.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HealthApi {

    @GetMapping(value = "/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok().body("Health is Good");
    }
}
