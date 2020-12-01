package kr.seok.security.aop;

import org.springframework.stereotype.Service;

@Service
public class AopMethodService {

    public void methodSecured() {
        System.out.println("methodSecured");
    }
}
