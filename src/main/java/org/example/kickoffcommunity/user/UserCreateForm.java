package org.example.kickoffcommunity.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm {

    @NotEmpty(message = "ID는 필수항목입니다.")
    @Size(max = 15, message = "ID는 최대 15자까지 가능합니다.")
    private String username;

    @NotEmpty(message = "학번은 필수항목입니다.")
    @Size(min = 8, max = 8, message = "8자리 학번을 입력해주세요.")
    private String studentnum;

    @NotEmpty(message = "이름은 필수항목입니다.")
    @Size(max = 20, message = "이름은 최대 20자까지 가능합니다.")
    private String name;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    @Size(min = 8, max=20, message = "비밀번호는 8~20자로 설정 가능합니다.")
    private String password1;

    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    private String password2;
}
