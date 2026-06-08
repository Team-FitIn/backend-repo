package team.fitin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequestDto {
    private String name;
    private String email;
    private String password;
    private Float height;
    private Float weight;
}
