package com.teamY.angryBox.vo;

import lombok.Getter;
import org.apache.ibatis.type.Alias;

import java.time.Instant;

@Getter
@Alias("DiaryVO")
public class DiaryVO {

    private int id;
    private int memberId;
    private String title;
    private String content;
    private Instant writeDate;
    private int isPublic; //0 : 비밀, 1 : 공개
    private int angryFigure;
    private int todackCount; //default 0
    private int coinBankId;
    private int diaryNo;

    //다이어리 초기 작성 시 사용할 생성자
    public DiaryVO(int memberId, String title, String content, Instant writeDate, int isPublic, int angryFigure, int coinBankId) {
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.writeDate = writeDate;
        this.isPublic = isPublic;
        this.angryFigure = angryFigure;
        this.coinBankId = coinBankId;
    }

}