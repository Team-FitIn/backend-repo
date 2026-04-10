package team.fitin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team.fitin.dto.LoginRequestDto;
import team.fitin.dto.SignUpRequestDto;
import team.fitin.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 회원가입 API
     */
    @PostMapping("/signup")
    public Long signup(@RequestBody SignUpRequestDto requestDto) {
        return authService.signup(requestDto);
    }

    /**
     * 로그인 API
     */
    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto requestDto) {
        return authService.login(requestDto);
    }
}
