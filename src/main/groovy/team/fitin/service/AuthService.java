package team.fitin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.fitin.domain.Member;
import team.fitin.dto.LoginRequestDto;
import team.fitin.dto.SignUpRequestDto;
import team.fitin.global.security.JwtTokenProvider;
import team.fitin.repository.MemberRepository;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 회원가입: 비밀번호를 암호화하여 DB에 저장합니다.
     */
    @Transactional
    public Long signup(SignUpRequestDto requestDto) {
        // 이메일 중복 검사
        if (memberRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RuntimeException("이미 가입된 이메일입니다.");
        }

        // Member 엔티티 빌드 및 저장
        Member member = Member.builder()
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .name(requestDto.getName())
                .height(requestDto.getHeight())
                .weight(requestDto.getWeight())
                .build();

        return memberRepository.save(member).getId();
    }

    /**
     * 로그인: 이메일/비번 확인 후 JWT 토큰을 반환합니다.
     */
    public String login(LoginRequestDto requestDto) {
        // 유저 존재 여부 확인
        Member member = memberRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new RuntimeException("가입되지 않은 이메일입니다."));

        // 비밀번호 일치 확인
        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 토큰 생성 및 반환
        return jwtTokenProvider.createToken(member.getEmail(), Collections.singletonList("ROLE_USER"));
    }
}
