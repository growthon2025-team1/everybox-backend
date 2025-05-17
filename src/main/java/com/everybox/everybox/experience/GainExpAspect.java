package com.everybox.everybox.experience;

import com.everybox.everybox.domain.User;
import com.everybox.everybox.experience.ExpType;
import com.everybox.everybox.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class GainExpAspect {

    private final UserRepository userRepository;

    @Around("@annotation(gainExp)")
    public Object handleExperience(ProceedingJoinPoint joinPoint, GainExp gainExp) throws Throwable {
        Object result = joinPoint.proceed(); // 원래 메서드 실행

        // 유저 ID를 파라미터에서 찾는다 (Long userId)
        Long userId = Arrays.stream(joinPoint.getArgs())
                .filter(arg -> arg instanceof Long)
                .map(arg -> (Long) arg)
                .findFirst()
                .orElse(null);

        if (userId != null) {
            User user = userRepository.findById(userId).orElseThrow();
            ExpType expType = gainExp.value(); // 전달받은 타입 확인

            // 경험치 증가
            user.gainExperience(expType.getExp());
            userRepository.save(user);
            log.info("사용자 {} : '{}' 완료, 경험치 {} xp 획득", userId, expType.getDescription(), expType.getExp());
        }
        return result;
    }
}
