package team.fitin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.fitin.domain.SocialAccount;
import java.util.Optional;

@Repository
public interface SocialAccountRepository extends JpaRepository<SocialAccount, Long> {
    //특정 공급자(KAKAO/GOOGLE)와 해당 서비스의 고유 ID로 계정 찾기
    Optional<SocialAccount> findByProviderAndProviderId(String provider, String providerId);
}
