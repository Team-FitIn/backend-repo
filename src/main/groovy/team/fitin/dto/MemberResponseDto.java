package team.fitin.dto;

import lombok.Builder;
import lombok.Getter;
import team.fitin.domain.Member;

@Getter
@Builder
public class MemberResponseDto {
    private String email;
    private Float height;
    private Float weight;
    private Float shoulderWidth;
    private Float legLength;

    public static MemberResponseDto from(Member member) {
        return MemberResponseDto.builder()
                .email(member.getEmail())
                .height(member.getHeight())
                .weight(member.getWeight())
                .shoulderWidth(member.getShoulderWidth())
                .legLength(member.getLegLength())
                .build();
    }
}
