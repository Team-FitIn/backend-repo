package team.fitin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team.fitin.dto.LoginRequestDto;
import team.fitin.dto.SignUpRequestDto;
import team.fitin.service.AuthService;

/**
 * 인증 및 권한 관련 API를 제공하는 컨트롤러입니다.
 */
@Tag(name = "인증(Auth)", description = "회원가입, 로그인 및 토큰 발급 관련 API")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입", description = "새로운 회원을 등록하고 고유 ID를 반환합니다.")
    @PostMapping("/signup")
    public Long signup(@RequestBody SignUpRequestDto requestDto) {
        return authService.signup(requestDto);
    }

    @Operation(summary = "로그인", description = "이메일과 비밀번호로 인증 후 JWT 액세스 토큰을 발급합니다.")
    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto requestDto) {
        return authService.login(requestDto);
    }
}
