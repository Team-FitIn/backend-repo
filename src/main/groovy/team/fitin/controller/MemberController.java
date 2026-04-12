package team.fitin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import team.fitin.dto.BodyInfoRequestDto;
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
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody BodyInfoRequestDto requestDto) {

        memberService.updateMemberBodyInfo(userDetails.getUsername(), requestDto);

        return "신체 정보가 성공적으로 업데이트되었습니다.";
    }
}
