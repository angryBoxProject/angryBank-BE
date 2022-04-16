package com.teamY.angryBox.repository;

import com.teamY.angryBox.mapper.FileMapper;
import com.teamY.angryBox.utils.FileManager;
import com.teamY.angryBox.vo.FileVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Repository
public class FileRepository {
    private final FileManager fileManager;
    private final FileMapper fileMapper;

    public FileVO uploadFile(MultipartFile file) {
        FileVO fileVO = fileManager.uploadFile(file);
        log.info("uploaded fileVO" + fileVO);
        fileMapper.insertFile(fileVO);
        log.info("inserted fileVO" + fileVO);
        return fileVO;
    }
}
