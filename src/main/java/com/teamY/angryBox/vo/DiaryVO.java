package com.teamY.angryBox.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Alias("DiaryVO")
public class DiaryVO {

    private int id;
    private int memberId;
    private boolean isDeleted;
    private int diaryNo;
    private String title;
    private String content;
    private String dateTime;
    private int angryPhaseId;
    private boolean isPublic;
    private int todackCount; //default 0
    private int coinBankId;
    private int viewCount;
    private int todayTopId;
    private int dailyTopId;


    //다이어리 작성 시 사용할 생성자
    public DiaryVO(int memberId, String title, String content, boolean isPublic, int angryPhaseId, int coinBankId) {
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.isPublic = isPublic;
        this.angryPhaseId = angryPhaseId;
        this.coinBankId = coinBankId;
    }

    public DiaryVO(int memberId, String title, String content, boolean isPublic, int angryPhaseId) {
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.isPublic = isPublic;
        this.angryPhaseId = angryPhaseId;
    }

    //다이어리 수정 시 사용할 생성자
    public DiaryVO(int id, int memberId, String title, String content, boolean isPublic, int angryPhaseId) {
        this.id = id;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.isPublic = isPublic;
        this.angryPhaseId = angryPhaseId;
    }




}

