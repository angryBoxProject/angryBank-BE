package com.teamY.angryBox.controller;

import com.teamY.angryBox.repository.FileRepository;
import com.teamY.angryBox.vo.FileVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ProfileController {

    private final FileRepository fileRepository;

    @PostMapping("filetest")
    public FileVO fileTest(MultipartFile file){

        return fileRepository.uploadFile(file);
    }

}
