package team.fitin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.fitin.domain.FittingHistory;
import team.fitin.domain.Member;
import team.fitin.dto.FittingHistoryResponseDto;
import team.fitin.repository.FittingHistoryRepository;
import team.fitin.repository.MemberRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FittingHistoryService {

    private final FittingHistoryRepository fittingHistoryRepository;
    private final MemberRepository memberRepository;

    /**
     * 특정 회원의 피팅 히스토리 최신순 조회
     */
    public List<FittingHistoryResponseDto> getHistoryByMember(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));

        return fittingHistoryRepository.findByMemberOrderByIdDesc(member).stream()
                .map(FittingHistoryResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 피팅 히스토리 단건 삭제 (내 기록인지 검증 후 삭제)
     */
    @Transactional
    public void deleteHistory(String email, Long historyId) {
        FittingHistory history = fittingHistoryRepository.findById(historyId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 피팅 기록입니다."));

        // 보안 검증: 현재 요청한 유저가 이 기록의 주인이 맞는지 확인
        if (!history.getMember().getEmail().equals(email)) {
            throw new RuntimeException("해당 기록을 삭제할 권한이 없습니다.");
        }

        fittingHistoryRepository.delete(history);
    }
}
