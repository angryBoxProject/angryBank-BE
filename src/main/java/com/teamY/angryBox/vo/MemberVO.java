package com.teamY.angryBox.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.ibatis.type.Alias;

@Getter
@AllArgsConstructor
@Alias("MemberVO")
public class MemberVO {
    private int id;
    private String email;
    private String nickname;
    private String password;
}
