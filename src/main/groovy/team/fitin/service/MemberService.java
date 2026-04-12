package team.fitin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.fitin.domain.Member;
import team.fitin.dto.BodyInfoRequestDto;
import team.fitin.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void updateMemberBodyInfo(String email, BodyInfoRequestDto requestDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("해당 회원을 찾을 수 없습니다."));

        member.updateBodyInfo(
                requestDto.getHeight(),
                requestDto.getWeight(),
                requestDto.getShoulderWidth(),
                requestDto.getLegLength()
        );
    }
}
