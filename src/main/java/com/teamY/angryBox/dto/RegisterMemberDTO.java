package com.teamY.angryBox.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ToString
@Getter
@AllArgsConstructor
public class RegisterMemberDTO {

    @Size(min=1, max=45, message="1자 이상 45자 이하로 작성해야합니다.")
    @NotBlank(message = "이메일 입력은 필수입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @Size(min=1, max=45, message="1자 이상 45자 이하로 작성해야합니다.")
    @NotBlank(message = "닉네임 입력은 필수입니다.")
    private String nickname;

    @Size(min=4, max=12, message="4자 이상 12자 이하로 작성해야합니다.")
    @NotBlank(message = "비밀번호 입력은 필수입니다.")
    private String password;
}
