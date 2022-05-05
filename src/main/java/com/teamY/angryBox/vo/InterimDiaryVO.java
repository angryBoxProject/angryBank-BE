package com.teamY.angryBox.vo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Alias("InterimDiaryVO")
public class InterimDiaryVO {

    private int id;
    private int memberId;
    private String title;
    private String content;
    private String dateTime;
    private boolean isPublic;
    private int angryPhaseId;

    public InterimDiaryVO(int memberId, String title, String content, boolean isPublic, int angryPhaseId) {
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.isPublic = isPublic;
        this.angryPhaseId = angryPhaseId;
    }

    public InterimDiaryVO(int id, int memberId, String title, String content, boolean isPublic, int angryPhaseId) {
        this.id = id;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.isPublic = isPublic;
        this.angryPhaseId = angryPhaseId;
    }
}
