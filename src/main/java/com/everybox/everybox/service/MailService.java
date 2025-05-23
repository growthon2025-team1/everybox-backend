package com.everybox.everybox.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class MailService {

    private final JavaMailSender mailSender;
    private final Map<Long, String> codeStore = new HashMap<>();              // userId -> 인증코드
    private final Map<Long, String> emailStore = new HashMap<>();             // userId -> 이메일
    private final Map<Long, LocalDateTime> codeIssuedAt = new HashMap<>();    // userId -> 발급 시각

    private static final int CODE_EXPIRATION_MINUTES = 5;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // 인증코드 발송
    public void sendVerificationCode(Long userId, String email) {
        String code = generateCode();
        codeStore.put(userId, code);
        emailStore.put(userId, email);
        codeIssuedAt.put(userId, LocalDateTime.now()); // 발급 시간 기록

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Everybox 이메일 인증코드");
        message.setText("인증코드: " + code + "\n유효시간: 5분");
        mailSender.send(message);
    }

    // 인증코드 검증
    public boolean verifyCode(Long userId, String code) {
        String savedCode = codeStore.get(userId);
        LocalDateTime issuedAt = codeIssuedAt.get(userId);

        if (savedCode == null || issuedAt == null) {
            return false;
        }

        // 유효시간 초과 확인
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(issuedAt.plusMinutes(CODE_EXPIRATION_MINUTES))) {
            // 시간 초과 → 코드 무효화
            codeStore.remove(userId);
            codeIssuedAt.remove(userId);
            return false;
        }

        if (savedCode.equals(code)) {
            // 인증 성공 → 코드 제거
            codeStore.remove(userId);
            codeIssuedAt.remove(userId);
            return true;
        }

        return false;
    }

    // 인증된 이메일 조회
    public String getVerifiedEmail(Long userId) {
        return emailStore.get(userId);
    }

    // 6자리 인증코드 생성
    private String generateCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}