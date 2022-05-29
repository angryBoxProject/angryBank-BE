package com.teamY.angryBox.dto;

import io.swagger.models.auth.In;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.List;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Alias("DiaryDTO")
public class DiaryDTO {

    private int id;
    private int memberId;
    private String title;
    private String content;
    private int angryPhaseId;
    private boolean isPublic;
    private int coinBankId;
    private List<Integer> removedFileId;
    private int interimId;

    public DiaryDTO(String title, String content, int angryPhaseId, boolean isPublic, int interimId) {
        this.title = title;
        this.content = content;
        this.angryPhaseId = angryPhaseId;
        this.isPublic = isPublic;
        this.interimId = interimId;
    }

    public DiaryDTO(int memberId, String title, String content, int angryPhaseId, boolean isPublic, int coinBankId) {
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.angryPhaseId = angryPhaseId;
        this.isPublic = isPublic;
        this.coinBankId = coinBankId;
    }

    public DiaryDTO(int id, int memberId, String title, String content, int angryPhaseId, boolean isPublic, List<Integer> removedFileId) {
        this.id = id;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.angryPhaseId = angryPhaseId;
        this.isPublic = isPublic;
        this.removedFileId = removedFileId;
    }


}
