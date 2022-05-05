package com.teamY.angryBox.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.ibatis.type.Alias;

@Getter
@AllArgsConstructor
@Alias("BankStatCalenderVO")
public class BankStatCalenderVO {
    private String writeDate;
    private int diaryId;
}
