package com.everybox.everybox.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private static final int CODE_EXPIRATION_MINUTES = 5;
    private final Map<String, VerificationCode> verificationStore = new ConcurrentHashMap<>();


    public void sendAuthMail(String toEmail, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("[모냉 : 모두의 냉장고] 이메일 인증 코드");
        message.setText("인증 코드는 " + code + " 입니다.");
        mailSender.send(message);
    }

    public String createCode() {
        return String.valueOf((int)(Math.random() * 900000) + 100000);  // 6자리 숫자
    }

    public void saveCode(String email, String code) {
        verificationStore.put(email, new VerificationCode(code, LocalDateTime.now().plusMinutes(CODE_EXPIRATION_MINUTES)));
    }

    public boolean verifyCode(String email, String code) {
        VerificationCode stored = verificationStore.get(email);
        if (stored == null || stored.getExpiresAt().isBefore(LocalDateTime.now())) {
            return false;
        }
        return stored.getCode().equals(code);
    }

}