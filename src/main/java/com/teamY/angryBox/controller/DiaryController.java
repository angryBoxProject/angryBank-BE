package com.teamY.angryBox.controller;

import com.teamY.angryBox.config.security.oauth.AuthTokenProvider;
import com.teamY.angryBox.config.security.oauth.MemberPrincipal;
import com.teamY.angryBox.dto.ResponseDataMessage;
import com.teamY.angryBox.dto.ResponseMessage;
import com.teamY.angryBox.repository.DiaryRepository;
import com.teamY.angryBox.repository.FileRepository;
import com.teamY.angryBox.service.DiaryService;
import com.teamY.angryBox.vo.DiaryFileVO;
import com.teamY.angryBox.vo.DiaryVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

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
    public ResponseEntity<ResponseMessage> registerDiary(@RequestParam String title, @RequestParam String content,
                                                         @RequestParam int isPublic, @RequestParam int angryFigure,
                                                         @RequestParam int coinBankId, @RequestBody MultipartFile[] file) {

        int memberId = ((MemberPrincipal )SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();
        log.info("/diary - memberId : " + memberId);

        DiaryVO diary = new DiaryVO(memberId, title, content, isPublic, angryFigure, coinBankId);
        diaryService.registerDiary(diary, file);

        return new ResponseEntity<>(new ResponseMessage(true, "다이어리 등록 성공", ""), HttpStatus.OK);
    }

    @GetMapping("diaries/coinBank/{coinBankId}")
    public ResponseEntity<ResponseDataMessage> retrieveDiaryListOneMember(@PathVariable int coinBankId) {
        Map<String, Object> data = new HashMap<>();

        int memberId = ((MemberPrincipal )SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();

        List<DiaryVO> diaryListInCoinBank = diaryService.retrieveDiaryListInCoinBank(memberId, coinBankId);
        data.put("diaryListInCoinBank", diaryListInCoinBank);

        return new ResponseEntity<>(new ResponseDataMessage(true, "다이어리 조회(저금통 별) 성공", "", data), HttpStatus.OK);
    }

    @GetMapping("diaries/{diaryId}")
    public ResponseEntity<ResponseDataMessage> retrieveDiaryDetail(@PathVariable int diaryId) {
        List<DiaryFileVO> diary = diaryService.retrieveDiaryDetaile(diaryId);
        Map<String, Object> data = new LinkedHashMap<>();
        
        for(int i = 0; i < diary.size(); i++) {
            data.put("diary", diary.get(i).getDiaryVO());
            if(diary.get(i).getFileVO() != null) {
                Map<String, Object> fileInfo = new HashMap<>();
                fileInfo.put("fileLink", "/images/" + diary.get(i).getFileVO().getSystemFileName());
                fileInfo.put("fileNo", diary.get(i).getFileVO().getFileNo());
                data.put("file" + (i + 1) + ": ", fileInfo);
            }
        }
        log.info("data : " + data);

        return new ResponseEntity<>(new ResponseDataMessage(true, "다이어리 상세조회 성공", "", data), HttpStatus.OK);
    }

    @DeleteMapping("diaries/{diaryId}")
    public ResponseEntity<ResponseMessage> removeDiary(@PathVariable int diaryId) {
        int memberId = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();
        if(diaryService.retrieveDiaryMemberId(diaryId, memberId) != 0) {
            diaryService.removeDiary(diaryId);

            return new ResponseEntity<>(new ResponseMessage(true, "다이어리 삭제 성공", ""), HttpStatus.OK);
        } else {

            return new ResponseEntity<>(new ResponseMessage(false, "다이어리 작성자와 삭제 요청자 불일치", "다이어리 작성자와 삭제 요청자 불일치"), HttpStatus.BAD_REQUEST);
        }
    }





}
