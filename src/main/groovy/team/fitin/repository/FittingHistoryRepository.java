package team.fitin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.fitin.domain.FittingHistory;
import team.fitin.domain.Member;
import java.util.List;

public interface FittingHistoryRepository extends JpaRepository<FittingHistory, Long> {
    List<FittingHistory> findByMemberOrderByIdDesc(Member member);
}
