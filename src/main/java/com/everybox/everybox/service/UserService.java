package com.everybox.everybox.service;

import com.everybox.everybox.domain.User;
import com.everybox.everybox.dto.UserLoginRequestDto;
import com.everybox.everybox.dto.UserSignupRequestDto;
import com.everybox.everybox.dto.UserUpdateRequestDto;
import com.everybox.everybox.dto.UserResponseDto;
import com.everybox.everybox.global.exception.CustomException;
import com.everybox.everybox.global.exception.ErrorCode;
import com.everybox.everybox.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MailService mailService;


    public void isValidId(String username) {
        // 중복 ID 체크
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
    }

    public UserResponseDto registerUser(UserSignupRequestDto request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .isVerified(false)
                .build();
        User saved = userRepository.save(user);
        return UserResponseDto.from(saved);
    }

    public void sendVerificationCode(Long userId, String email) {
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.ac\\.kr$")) {
            throw new IllegalArgumentException("학교 이메일(@xxx.ac.kr)만 인증 가능합니다.");
        }
        // 이미 인증된 이메일인지 확인
        userRepository.findByUniversityEmail(email).ifPresent(user -> {
            if (user.getIsVerified() && !user.getId().equals(userId)) {
                throw new IllegalArgumentException("이미 다른 계정에서 인증된 이메일입니다.");
            }
        });
        mailService.sendVerificationCode(userId, email);
    }

    public void verifyCode(Long userId, String code) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (mailService.verifyCode(userId, code)) {
            String verifiedEmail = mailService.getVerifiedEmail(userId); // 인증된 이메일 조회
            // 중복 체크는 이미 sendVerificationCode에서 했으므로 바로 저장
            user.setUniversityEmail(verifiedEmail);
            user.setIsVerified(true);
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("인증코드가 올바르지 않거나 유효시간을 초과하였습니다.");
        }
    }

    public UserResponseDto login(UserLoginRequestDto request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 아이디입니다."));
        if (user.getPassword() == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.USER_PASSWORD_MISMATCH);
        }
        return UserResponseDto.from(user);
    }

    public UserResponseDto findDtoById(Long id) {
        return userRepository.findById(id).map(UserResponseDto::from).orElse(null);
    }

    public UserResponseDto findOrCreateKakaoUserDto(String email, String nickname) {

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("카카오 이메일 정보가 없습니다.");
        }

        Optional<User> optionalUser = userRepository.findByUsername(email);
        if (optionalUser.isPresent()) {
            return UserResponseDto.from(optionalUser.get());
        }

        User newUser = User.builder()
                .username(email)     // username은 email로
                .nickname(nickname)  // 전달받은 nickname 그대로
                .password(null)      // 소셜 로그인 사용자는 비밀번호 없음
                .isVerified(false)   // 학교 인증 X
                .build();

        userRepository.save(newUser);
        return UserResponseDto.from(newUser);
    }

    public User findOrCreateKakaoUser(String username, String nickname) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("username(이메일)이 없습니다!");
        }
        return userRepository.findByUsername(username)
                .orElseGet(() -> {
                    User user = User.builder()
                            .username(username)
                            .nickname(nickname)
                            .password(null)
                            .isVerified(false)
                            .build();
                    return userRepository.save(user);
                });
    }

    public UserResponseDto updateUser(Long userId, Long loginUserId, UserUpdateRequestDto request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (!user.getId().equals(loginUserId)) {
            throw new IllegalArgumentException("본인만 수정할 수 있습니다.");
        }
        if (request.getNickname() != null) user.setNickname(request.getNickname());
        if (request.getPassword() != null) user.setPassword(passwordEncoder.encode(request.getPassword()));
        return UserResponseDto.from(userRepository.save(user));
    }

    public void deleteUser(Long userId, Long loginUserId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (!user.getId().equals(loginUserId)) {
            throw new IllegalArgumentException("본인만 삭제할 수 있습니다.");
        }
        userRepository.delete(user);
    }
}
