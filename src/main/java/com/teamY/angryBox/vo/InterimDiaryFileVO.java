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
@Alias("InterimDiaryFileVO")
public class InterimDiaryFileVO {

    private InterimDiaryVO interimDiaryVO;
    private FileVO fileVO;
}
