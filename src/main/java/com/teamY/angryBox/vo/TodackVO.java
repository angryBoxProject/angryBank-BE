package com.teamY.angryBox.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@ToString
@Getter
@AllArgsConstructor
@Alias("TodackVO")
public class TodackVO {

    private int id;
    private int sendMemberId;
    private int receiveMemberId;
    private int diaryId;
    private int sendReceive; //send = 0, receive = 1

    public TodackVO() {
    }

    public TodackVO(int diaryId, int sendMemberId, int receiveMemberId) {
        this.diaryId = diaryId;
        this.sendMemberId = sendMemberId;
        this.receiveMemberId = receiveMemberId;
    }
}
