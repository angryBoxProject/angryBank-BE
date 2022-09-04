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

}
