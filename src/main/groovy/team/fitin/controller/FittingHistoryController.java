package team.fitin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import team.fitin.dto.FittingHistoryResponseDto;
import team.fitin.service.FittingHistoryService;

import java.util.List;

@Tag(name = "Fitting History", description = "사용자 피팅 결과 기록 및 조회 API")
@RestController
@RequestMapping("/api/fitting/history")
@RequiredArgsConstructor
public class FittingHistoryController {

    private final FittingHistoryService fittingHistoryService;

    @Operation(summary = "유저별 피팅 기록 전체 조회", description = "현재 로그인한 사용자가 여태까지 시도했던 모든 가상 피팅 결과 목록(이미지 URL 등)을 최신순으로 가져옵니다.")
    @GetMapping
    public List<FittingHistoryResponseDto> getMyFittingHistory(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails
    ) {
        return fittingHistoryService.getHistoryByMember(userDetails.getUsername());
    }

    @Operation(summary = "특정 피팅 기록 삭제", description = "마이페이지 히스토리 내역에서 마음에 안 드는 특정 피팅 결과 기록을 삭제합니다.")
    @DeleteMapping("/{historyId}")
    public String deleteHistory(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("historyId") Long historyId
    ) {
        fittingHistoryService.deleteHistory(userDetails.getUsername(), historyId);
        return "피팅 기록이 삭제되었습니다.";
    }
}
