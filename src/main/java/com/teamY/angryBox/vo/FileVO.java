package com.teamY.angryBox.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Alias("FileVO")
public class FileVO {
    private int id;
    private String originalFileName;
    private String systemFileName;
    private long fileSize;
    private String fileType;
    private String dateTime;

    public FileVO(String originalFileName, String systemFileName, String fileType, long fileSize) {
        this.originalFileName = originalFileName;
        this.systemFileName = systemFileName;
        this.fileSize = fileSize;
        this.fileType = fileType;
    }

    public FileVO(int id, String originalFileName, String systemFileName) {
        this.id = id;
        this.originalFileName = originalFileName;
        this.systemFileName = systemFileName;
    }
}
