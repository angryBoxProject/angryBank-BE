package com.teamY.angryBox.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.util.List;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Alias("InterimDiaryDTO")
public class InterimDiaryDTO {

    private int id;
    private int memberId;
    private String title;
    private String content;
    private int angryPhaseId;
    private boolean isPublic;
    private List<Integer> removedFileId;

    public InterimDiaryDTO(int memberId, String title, String content, int angryPhaseId, boolean isPublic) {
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.angryPhaseId = angryPhaseId;
        this.isPublic = isPublic;
    }
}
