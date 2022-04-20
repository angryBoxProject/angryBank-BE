package com.teamY.angryBox.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;


@ToString
@AllArgsConstructor
@Getter
//@Alias("TopDiaryDTO")
public class TopDiaryDTO {

    private int writeYear;
    private int writeMonth;
    private int writeDay;
    private int isPublic;
    private int limit;
}
