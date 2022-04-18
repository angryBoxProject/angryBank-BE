package com.teamY.angryBox.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Alias("DiaryVO")
public class DiaryVO {

    private int id;
    private int memberId;
    private int diaryNo;
    private String title;
    private String content;
    private String dateTime;
    private int angryFigure; // 수치로 받아올지 string으로 받아올지 논의 해보고 받아올 타입 변경하든지 해야 할듯
    private String angryName;
    private int isPublic;
    private int todackCount; //default 0
    private int coinBankId;


    //다이어리 작성 시 사용할 생성자
    public DiaryVO(int memberId, String title, String content, int isPublic, int angryFigure, int coinBankId) {
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.isPublic = isPublic;
        this.angryFigure = angryFigure;
        this.coinBankId = coinBankId;
    }


}

