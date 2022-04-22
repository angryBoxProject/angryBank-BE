package com.teamY.angryBox.controller;

import com.teamY.angryBox.config.security.oauth.MemberPrincipal;
import com.teamY.angryBox.dto.ResponseDataMessage;
import com.teamY.angryBox.dto.ResponseMessage;

import com.teamY.angryBox.service.DiaryService;
import com.teamY.angryBox.vo.DiaryFileVO;
import com.teamY.angryBox.vo.DiaryVO;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class DiaryController {

    private final DiaryService diaryService;


    @PostMapping("diary")
    public ResponseEntity<ResponseMessage> createDiary(@RequestParam String title, @RequestParam String content,
                                                         @RequestParam int isPublic, @RequestParam int angryPhaseId,
                                                         @RequestParam int coinBankId, @RequestBody MultipartFile[] file) {

        int memberId = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();
        DiaryVO diary = new DiaryVO(memberId, title, content, isPublic, angryPhaseId, coinBankId);

        diaryService.addDiary(diary, file);

        return new ResponseEntity<>(new ResponseMessage(true, "다이어리 등록 성공", ""), HttpStatus.OK);

    }

    @GetMapping("diaries/coinBank/{coinBankId}/{lastDiaryId}/{size}")
    public ResponseEntity<ResponseDataMessage> inquriyDiaryListCoinBank(@PathVariable int coinBankId, @PathVariable int lastDiaryId, @PathVariable int size) {
        Map<String, Object> data = new HashMap<>();
        int memberId = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();
        List<DiaryVO> diaryListInCoinBank = diaryService.getDiaryListInCoinBank(memberId, coinBankId, lastDiaryId, size);

        data.put("diaryListInCoinBank", diaryListInCoinBank);

        return new ResponseEntity<>(new ResponseDataMessage(true, "다이어리 조회(저금통별) 성공", "", data), HttpStatus.OK);
    }

    @GetMapping("diaries/month/{date}/{lastDiaryId}/{size}")
    public ResponseEntity<ResponseDataMessage> inquriyDiaryListInMonth(@PathVariable String date, @PathVariable int lastDiaryId, @PathVariable int size) {
        Map<String, Object> data = new HashMap<>();
        int memberId = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();
        List<DiaryVO> diaryListInMonth = diaryService.getDiaryListInMonth(memberId, date, lastDiaryId, size);

        data.put("diaryListInMonth", diaryListInMonth);

        return new ResponseEntity<>(new ResponseDataMessage(true, "다이어리 조회(월별) 성공", "", data), HttpStatus.OK);
    }

    @GetMapping("diaries/dailyTop/{date}/{lastDiaryId}/{size}")
    public ResponseEntity<ResponseDataMessage> inquriyDailyTopDiary(@PathVariable String date, @PathVariable int lastDiaryId, @PathVariable int size) {
        Map<String, Object> data = new HashMap<>();

        data.put("dailyTopDiary", diaryService.getDailyTop(date, lastDiaryId, size));

        return new ResponseEntity<>(new ResponseDataMessage(true, "다이어리 조회(Daily TOP) 성공", "", data), HttpStatus.OK);
    }

    @GetMapping("diaries/todayTop/{lastDiaryId}/{size}")
    public ResponseEntity<ResponseDataMessage> inquriyTodayTopDiary(@PathVariable int lastDiaryId, @PathVariable int size) {
        Map<String, Object> data = new HashMap<>();

        data.put("todayTopDiary", diaryService.getTodayTop(lastDiaryId, size));

        return new ResponseEntity<>(new ResponseDataMessage(true, "다이어리 조회(Today TOP) 성공", "", data), HttpStatus.OK);
    }


    @GetMapping("diaries/{diaryId}")
    public ResponseEntity<ResponseMessage> inquriyDiaryDetail(@PathVariable int diaryId) {
        int memberId = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();

        Map<String, Object> data = diaryService.getDiaryDetail(diaryId, memberId);

        return new ResponseEntity<>(new ResponseDataMessage(true, "다이어리 상세조회 성공", "", data), HttpStatus.OK);

    }

    @DeleteMapping("diaries/{diaryId}")
    public ResponseEntity<ResponseMessage> removeDiary(@PathVariable int diaryId) {
        int memberId = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();

        diaryService.removeDiary(diaryId, memberId);

        return new ResponseEntity<>(new ResponseMessage(true, "다이어리 삭제 성공", ""), HttpStatus.OK);
    }

    @PutMapping("diaries/{diaryId}")
    public ResponseEntity<ResponseMessage> modifyDiary(@PathVariable int diaryId,
                                                       @RequestParam String title, @RequestParam String content,
                                                       @RequestParam int isPublic, @RequestParam int angryPhaseId,
                                                       @RequestBody MultipartFile[] file, @RequestParam List removedFileId) {

        int memberId = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();

        DiaryVO diaryVO = new DiaryVO(diaryId, memberId, title, content, isPublic, angryPhaseId);
        diaryService.changeDiary(diaryVO, file, removedFileId);

        return new ResponseEntity<>(new ResponseMessage(true, "다이어리 수정 성공", ""), HttpStatus.OK);

    }

    @GetMapping("diaries/search/{keyword}/{lastDiaryId}/{size}")
    public ResponseEntity<ResponseDataMessage> searchDiary(@PathVariable String keyword, @PathVariable int lastDiaryId, @PathVariable int size) {

        Map<String, Object> data = new HashMap<>();

        data.put("diaries", diaryService.searchDiary(keyword, lastDiaryId, size));

        return new ResponseEntity<>(new ResponseDataMessage(true, "검색 성공", "", data), HttpStatus.OK);
    }

    @GetMapping("diaries")
    public ResponseEntity<ResponseDataMessage> diaries(@RequestParam int lastDiaryId, @RequestParam int size) {

        Map<String, Object> data = new HashMap<>();
        data.put("diaries", diaryService.bambooGrove(lastDiaryId, size));
        return new ResponseEntity<ResponseDataMessage>(new ResponseDataMessage(true, "대나무숲 조회 성공", "", data), HttpStatus.OK);
    }

}
