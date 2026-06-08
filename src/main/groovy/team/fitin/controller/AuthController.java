package team.fitin.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import team.fitin.dto.LoginRequestDto;
import team.fitin.dto.SignUpRequestDto;
import team.fitin.service.AuthService;
import team.fitin.service.MemberService;

import java.util.Map;

/**
 * 인증 및 권한 관련 API를 제공하는 컨트롤러입니다.
 */
@Tag(name = "인증(Auth)", description = "회원가입, 로그인 및 토큰 발급 관련 API")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final MemberService memberService;

    @Operation(summary = "회원가입", description = "새로운 회원을 등록하고 고유 ID를 반환합니다.")
    @PostMapping("/signup")
    public Long signup(@RequestBody SignUpRequestDto requestDto) {
        return authService.signup(requestDto);
    }

    @Operation(summary = "로그인", description = "이메일과 비밀번호로 인증 후 JWT 액세스 토큰을 발급합니다.")
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequestDto requestDto) {
        return Map.of("token", authService.login(requestDto));
    }

    @Operation(summary = "소셜 및 일반 회원 탈퇴", description = "현재 로그인한 사용자의 토큰을 검증하여 계정 정보와 연관된 피팅 히스토리를 DB에서 완전히 삭제합니다.")
    @DeleteMapping("/withdraw")
    public ResponseEntity<String> withdraw(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new RuntimeException("인증 정보가 만료되었거나 없습니다. 다시 로그인해 주세요.");
        }

        // MemberService를 호출하여 유저 이메일(username) 기반으로 DB 레코드 삭제
        memberService.deleteMember(userDetails.getUsername());
        return ResponseEntity.ok("회원 탈퇴가 성공적으로 완료되었습니다. 이용해 주셔서 감사합니다.");
    }
}

