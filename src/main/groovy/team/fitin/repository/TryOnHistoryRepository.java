package team.fitin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.fitin.domain.Member;
import team.fitin.domain.TryOnHistory;
import java.util.List;

@Repository
public interface TryOnHistoryRepository extends JpaRepository<TryOnHistory, Long> {

    // 특정 회원의 모든 피팅 기록을 최신순으로 가져오기
    List<TryOnHistory> findByMemberOrderByCreatedAtDesc(Member member);
}
