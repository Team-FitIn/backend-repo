package team.fitin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.fitin.domain.Member;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
