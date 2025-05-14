package com.everybox.everybox.mail;

import com.everybox.everybox.mail.dto.EmailRequest;
import com.everybox.everybox.mail.dto.VerificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class MailController {

    private final MailService mailService;

    @PostMapping("/send-code")
    public ResponseEntity<String> sendVerificationCode(@RequestBody EmailRequest request) {
        String code = mailService.createCode();
        mailService.saveCode(request.getEmail(), code);
        mailService.sendAuthMail(request.getEmail(), code);
        return ResponseEntity.ok("인증 코드가 이메일로 전송되었습니다.");
    }

    @PostMapping("/verify-code")
    public ResponseEntity<String> verifyCode(@RequestBody VerificationRequest request) {
        boolean isValid = mailService.verifyCode(request.getEmail(), request.getCode());
        if (isValid) {
            return ResponseEntity.ok("인증 성공");
        } else {
            return ResponseEntity.badRequest().body("인증 실패: 코드가 유효하지 않거나 만료되었습니다.");
        }
    }
}