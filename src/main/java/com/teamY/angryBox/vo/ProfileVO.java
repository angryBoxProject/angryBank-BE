package com.teamY.angryBox.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.ibatis.type.Alias;


@Getter
@AllArgsConstructor
@Alias("ProfileVO")
public class ProfileVO {
    private int id;
    private int memberId;
    private int fileId;

    public ProfileVO(int memberId, int fileId) {
        this.memberId = memberId;
        this.fileId = fileId;
    }
}
