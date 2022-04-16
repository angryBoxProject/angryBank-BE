package com.teamY.angryBox.controller;

import com.teamY.angryBox.dto.ResponseMessage;
import com.teamY.angryBox.repository.DiaryRepository;
import com.teamY.angryBox.vo.DiaryVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class DiaryController {

    private final DiaryRepository diaryRepository;

    @PostMapping("diary")
    public ResponseEntity<ResponseMessage> diaryRegister(@RequestBody DiaryVO diary) {
        log.info("컨트롤러 들어옴");
        diaryRepository.insertDiary(diary);
        return new ResponseEntity<>(new ResponseMessage(true, "다이어리 등록 성공", ""), HttpStatus.OK);
    }
}
