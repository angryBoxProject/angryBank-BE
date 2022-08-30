package com.teamY.angryBox.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.ibatis.type.Alias;

@Getter
@AllArgsConstructor
@Alias("NotificationListVO")
public class NotificationListVO {

    private int id;
    private int diaryId;
    private String content;
    private boolean checked; // 0 : 미확인, 1 : 확인
    private String dateTime;

}
