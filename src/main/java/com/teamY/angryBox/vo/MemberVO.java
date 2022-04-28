package com.teamY.angryBox.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Getter
@ToString
//@AllArgsConstructor
@Alias("MemberVO")
public class MemberVO {

    private int id;
    private String email;
    private String nickname;
    @JsonIgnore
    private String password;
    private String registerType;
    private int diaryCount;
    private int sendTodakCount;
    private int recieveTodakCount;
    private String lastLogin;

    public MemberVO(int id, String email, String nickname, String password, String registerType) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.registerType = registerType;
    }
    public MemberVO(int id, String email, String nickname, String registerType) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.registerType = registerType;
    }

    public MemberVO(String email, String nickname, String password, String registerType) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.registerType = registerType;
    }

    public MemberVO(int id, String email, String nickname) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
    }

    public MemberVO(int id, String email, String nickname, int diaryCount, int sendTodakCount, int recieveTodakCount) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.diaryCount = diaryCount;
        this.sendTodakCount = sendTodakCount;
        this.recieveTodakCount = recieveTodakCount;
    }

    public MemberVO(int id, String email, String nickname, int diaryCount, int sendTodakCount, int recieveTodakCount, String lastLogin) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.diaryCount = diaryCount;
        this.sendTodakCount = sendTodakCount;
        this.recieveTodakCount = recieveTodakCount;
        this.lastLogin = lastLogin;
    }
}

