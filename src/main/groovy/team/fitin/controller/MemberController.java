package team.fitin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter; // 추가
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import team.fitin.dto.BodyInfoRequestDto;
import team.fitin.dto.MemberResponseDto;
import team.fitin.service.MemberService;

@Tag(name = "회원(Member)", description = "회원 정보 관리 API")
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "신체 정보 업데이트", description = "로그인한 사용자의 키, 몸무게 등 신체 데이터를 수정합니다.")
    @PatchMapping("/body-info")
    public String updateBodyInfo(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails, // @Parameter(hidden = true) 추가
            @RequestBody BodyInfoRequestDto requestDto) {

        memberService.updateMemberBodyInfo(userDetails.getUsername(), requestDto);
        return "신체 정보가 성공적으로 업데이트되었습니다.";
    }

    @Operation(summary = "내 정보 조회", description = "현재 로그인한 사용자의 프로필 정보를 조회합니다.")
    @GetMapping("/me")
    public MemberResponseDto getMyInfo(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails // @Parameter(hidden = true) 추가
    ) {
        if (userDetails == null) {
            throw new RuntimeException("인증 정보가 없습니다. 다시 로그인해 주세요.");
        }
        return memberService.getMemberInfo(userDetails.getUsername());
    }
}
