package com.teamY.angryBox.utils;


import com.teamY.angryBox.vo.FileVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Component
public class FileManager {

    private String uploadPath = "/upload/";

    // 저장 경로 폴더가 없으면 폴더 생성
    public void init() {
        try {
            Files.createDirectories(Paths.get(uploadPath));
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload folder!");
        }
    }

    public FileVO uploadFile(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new RuntimeException("file is empty");
        }

        Path root = Paths.get(uploadPath);


        if (!Files.exists(root))
            init();

        String originalFileName = multipartFile.getOriginalFilename();
        String extension = extract(originalFileName);
        String systemFileName = createSystemFileName(originalFileName, extension);

        try {
            String path = root.toAbsolutePath() + "/" + systemFileName;
            log.info("파일 올리는 경로 : " + path);
            multipartFile.transferTo(new java.io.File(path));
        } catch (IOException e) {
            throw new RuntimeException("file transfer fail");
        }

        return new FileVO(originalFileName, systemFileName, extension, multipartFile.getSize());
    }


    public boolean deleteFile(String systemFileName) {

        Path root = Paths.get(uploadPath);
        //현재 게시판에 존재하는 파일객체를 만듬
        java.io.File file = new java.io.File(root.toAbsolutePath() + "\\" + systemFileName);
        // 파일이 존재하면
        if (file.exists()) {
            // 파일 삭제
            file.delete();
            return true;
        }

        return false;
    }

    private String createSystemFileName(String originalFileName, String extension) {
        return UUID.randomUUID().toString() + "." + extension;
    }

    private String extract(String originalFileName) {
        return originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
    }

}