package com.teamY.angryBox.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.Alias;

@ToString
@Getter
@AllArgsConstructor
@Slf4j
@Alias("ProfileJoinVO")
public class ProfileJoinVO {
    private int id;
    private MemberVO member;
    private FileVO file;

    public ProfileJoinVO(int id, int memberId, String email, String nickname, int diaryCount, int sendTodakCount, int recieveCount, String lastLogin, int fileId, String originalFileName, String systemFileName) {
        this.id = id;
        this.member = new MemberVO(memberId, email, nickname, diaryCount, sendTodakCount, recieveCount, lastLogin);
        this.file = new FileVO(fileId, originalFileName, systemFileName);
    }
}
