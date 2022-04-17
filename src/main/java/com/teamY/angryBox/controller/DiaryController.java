package com.teamY.angryBox.controller;

import com.teamY.angryBox.config.security.oauth.AuthTokenProvider;
import com.teamY.angryBox.config.security.oauth.MemberPrincipal;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
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
    private final AuthTokenProvider authTokenProvider;

    @Transactional
    @PostMapping("diary")
    public ResponseEntity<ResponseMessage> diaryRegister(@RequestParam String title, @RequestParam String content,
                                                         @RequestParam int isPublic, @RequestParam int angryFigure,
                                                         @RequestParam int coinBankId, @RequestBody MultipartFile[] file) {

        //int memberId = 1;
        int memberId = ((MemberPrincipal )SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();
        log.info("memberId : " + memberId);
//
        DiaryVO diary = new DiaryVO(memberId, title, content, isPublic, angryFigure, coinBankId);
        diaryService.registerDiary(diary, file);

        return new ResponseEntity<>(new ResponseMessage(true, "다이어리 등록 성공", ""), HttpStatus.OK);
    }

}
