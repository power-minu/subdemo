package minu.subran.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import minu.subran.domain.Member;

@Getter
@Setter
@NoArgsConstructor
public class JoinRequest {

    @NotBlank(message = "이메일이 비어있습니다.")
    private String memberEmail;

    @NotBlank(message = "비밀번호가 비어있습니다.")
    private String password;
    private String passwordCheck;

    @NotBlank(message = "닉네임이 비어있습니다.")
    private String memberName;

    public Member toEntity() {
        return Member.builder()
                .email(this.memberEmail)
                .password(this.password)
                .name(this.memberName)
                .build();
    }
}