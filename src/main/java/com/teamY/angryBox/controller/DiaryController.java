package com.teamY.angryBox.controller;

import com.teamY.angryBox.dto.ResponseMessage;
import com.teamY.angryBox.repository.DiaryRepository;
import com.teamY.angryBox.repository.FileRepository;
import com.teamY.angryBox.service.DiaryService;
import com.teamY.angryBox.vo.DiaryVO;
import com.teamY.angryBox.vo.FileVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class DiaryController {

    private final DiaryRepository diaryRepository;
    private final DiaryService diaryService;
    private final FileRepository fileRepository;

    @PostMapping("diary")
    public ResponseEntity<ResponseMessage> diaryRegister(@RequestParam String title, @RequestParam String content,
                                                         @RequestParam int isPublic, @RequestParam int angryFigure,
                                                         @RequestParam int coinBankId, @RequestBody MultipartFile[] file) {
        int memberId = 1;
        DiaryVO diary = new DiaryVO(memberId, title, content, isPublic, angryFigure, coinBankId);
        int diaryId = diaryService.registerDiary(diary);
        log.info(String.valueOf(diaryId));

        List<Integer> fileIdList = new ArrayList<>();
        if(file != null) {
            for(MultipartFile f : file) {
                fileIdList.add(fileRepository.uploadFile(f).getId());
            }
            for(int i = 0; i < fileIdList.size(); i++) {
                diaryService.registerDiaryFile(diaryId, fileIdList.get(i));
            }
        }

        return new ResponseEntity<>(new ResponseMessage(true, "다이어리 등록 성공", ""), HttpStatus.OK);
    }
}
