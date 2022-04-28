package com.teamY.angryBox.dto;

import lombok.*;
import org.apache.ibatis.type.Alias;

@ToString
@AllArgsConstructor
@Getter
@Alias("NotificationDTO")
public class NotificationDTO {

    private int id;
    private int diaryId;
    private int sendMemberId;
    private int receiveMemberId;
    @Setter
    private String content;
    private boolean checked;
    private String dateTime;

    public NotificationDTO(int diaryId, int sendMemberId, int receiveMemberId) {
        this.diaryId = diaryId;
        this.sendMemberId = sendMemberId;
        this.receiveMemberId = receiveMemberId;
    }

    public NotificationDTO(int id, int diaryId, String content, String dateTime, boolean checked) {
        this.id = id;
        this.diaryId = diaryId;
        this.content = content;
        this.dateTime = dateTime;
        this.checked = checked;
    }
}
