package team.fitin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.fitin.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    //Optional<Member> findByEmail(String email); 나중에 이메일로 찾아야할시 사용가능
}
