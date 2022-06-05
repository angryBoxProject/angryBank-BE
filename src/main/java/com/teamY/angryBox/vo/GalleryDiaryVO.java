package com.teamY.angryBox.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@ToString
@Getter
@Alias("GalleryDiaryVO")
@AllArgsConstructor
public class GalleryDiaryVO {
    int id;
    String title;
    int todack_count;
    String systemFileName;


}
