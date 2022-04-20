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
    private int angryPhaseId;
    private int isPublic;
    private int todackCount; //default 0
    private int coinBankId;
    private int viewCount;


    //다이어리 작성 시 사용할 생성자
    public DiaryVO(int memberId, String title, String content, int isPublic, int angryPhaseId, int coinBankId) {
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.isPublic = isPublic;
        this.angryPhaseId = angryPhaseId;
        this.coinBankId = coinBankId;
    }

    //다이어리 수정 시 사용할 생성자
    public DiaryVO(int id, int memberId, String title, String content, int isPublic, int angryPhaseId) {
        this.id = id;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.isPublic = isPublic;
        this.angryPhaseId = angryPhaseId;
    }


}

