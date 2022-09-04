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
@Alias("DiaryFileVO")
public class DiaryFileVO {

    private int id;
    private String systemFileName;
    private int fileNo;

}
