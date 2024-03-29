package com.teamY.angryBox.controller;

import com.teamY.angryBox.config.security.oauth.MemberPrincipal;
import com.teamY.angryBox.dto.*;
import com.teamY.angryBox.service.DiaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class DiaryController {

    private final DiaryService diaryService;


    @PostMapping("diary")
    public ResponseEntity<ResponseMessage> createDiary(@RequestParam("public") boolean isPublic,
                                                       @RequestPart(value = "diary") DiaryDTO diaryDTO,
                                                       @RequestPart(value = "file", required = false) MultipartFile[] file) {

        int memberId = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();

        diaryService.addDiary(diaryDTO, file, isPublic, memberId);

        int interimId = diaryDTO.getInterimId();
        if(interimId != 0) {
            diaryService.removeInterimDiary(interimId, memberId);
        }

        return new ResponseEntity<>(new ResponseMessage(true, "다이어리 등록 성공", ""), HttpStatus.OK);
    }


    @GetMapping("diaries/coinBank/{coinBankId}/{lastDiaryId}/{size}")
    public ResponseEntity<ResponseDataMessage> inquiryDiaryListCoinBank(@PathVariable int coinBankId, @PathVariable int lastDiaryId, @PathVariable int size) {

        int memberId = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();
        Map<String, Object> data = diaryService.getDiaryListInCoinBank(memberId, coinBankId, lastDiaryId, size);

        return new ResponseEntity<>(new ResponseDataMessage(true, "다이어리 조회(적금별) 성공", "", data), HttpStatus.OK);
    }

    @GetMapping("diaries/month/{date}/{lastDiaryId}/{size}")
    public ResponseEntity<ResponseDataMessage> inquiryDiaryListInMonth(@PathVariable String date, @PathVariable int lastDiaryId, @PathVariable int size) {

        int memberId = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();
        Map<String, Object> data = diaryService.getDiaryListInMonth(memberId, date, lastDiaryId, size);

        return new ResponseEntity<>(new ResponseDataMessage(true, "다이어리 조회(월별) 성공", "", data), HttpStatus.OK);
    }

    @GetMapping("diaries/dailyTop/{date}/{lastDiaryId}/{size}")
    public ResponseEntity<ResponseDataMessage> inquiryDailyTopDiary(@PathVariable String date, @PathVariable int lastDiaryId, @PathVariable int size) {

        Map<String, Object> data = diaryService.getDailyTop(date, lastDiaryId, size);

        return new ResponseEntity<>(new ResponseDataMessage(true, "다이어리 조회(Daily TOP) 성공", "", data), HttpStatus.OK);
    }

    @GetMapping("diaries/todayTop/{lastDiaryId}/{size}")
    public ResponseEntity<ResponseDataMessage> inquiryTodayTopDiary(@PathVariable int lastDiaryId, @PathVariable int size) {

        Map<String, Object> data = diaryService.getTodayTop(lastDiaryId, size);

        return new ResponseEntity<>(new ResponseDataMessage(true, "다이어리 조회(Today TOP) 성공", "", data), HttpStatus.OK);
    }


    @GetMapping("diaries/{diaryId}")
    public ResponseEntity<ResponseMessage> inquiryDiaryDetail(@PathVariable int diaryId) {
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
                                                       @RequestParam("public") boolean isPublic,
                                                       @RequestPart(value = "diary") DiaryDTO diaryDTO,
                                                       @RequestPart(value = "file", required = false) MultipartFile[] file) {

        int memberId = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();

        DiaryDTO diary = new DiaryDTO(diaryId, memberId, diaryDTO.getTitle(), diaryDTO.getContent(), diaryDTO.getAngryPhaseId(), isPublic, diaryDTO.getRemovedFileId());
        diaryService.changeDiary(diary, file);

        return new ResponseEntity<>(new ResponseMessage(true, "다이어리 수정 성공", ""), HttpStatus.OK);

    }

    @GetMapping("diaries/search/{keyword}/{lastDiaryId}/{size}")
    public ResponseEntity<ResponseDataMessage> searchDiary(@PathVariable String keyword, @PathVariable int lastDiaryId, @PathVariable int size) {

        Map<String, Object> data = new HashMap<>();

        data.put("diaries", diaryService.searchDiary(keyword, lastDiaryId, size));

        return new ResponseEntity<>(new ResponseDataMessage(true, "검색 성공", "", data), HttpStatus.OK);
    }

    /*
    String startDate = "1";
    String endDate = "1";
    int angry[];
    int imageFilter = 2; // 0 : 이미지 없는거만, 1: 이미지 있는거만, 2 : 이미지 있/없 둘다,

* */
    @PostMapping("diaries")
    public ResponseEntity<ResponseDataMessage> diaries(@RequestParam int lastDiaryId, @RequestParam int size
            , /*@RequestParam String startDate, @RequestParam String endDate, @RequestParam int angry[], @RequestParam int imageFilter*/ @RequestBody FilterDTO filter) {

        //FilterDTO filter = new FilterDTO(startDate, endDate, angry, imageFilter);
        log.info("filter : " + filter.toString());
        Map<String, Object> data = new HashMap<>();
        data.put("diaries", diaryService.bambooGrove(lastDiaryId, size, filter));
        return new ResponseEntity<ResponseDataMessage>(new ResponseDataMessage(true, "대나무숲 조회 성공", "", data), HttpStatus.OK);
    }

    @PostMapping("/interim-diary")
    public ResponseEntity<ResponseMessage> createInterimDiary(@RequestParam("public") boolean isPublic,
                                                              @RequestPart(value = "interimDiary")InterimDiaryDTO interimDiaryDTO,
                                                              @RequestPart(value = "file") MultipartFile[] file) {

        int memberId = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();

        InterimDiaryDTO interimDiary = new InterimDiaryDTO(memberId, interimDiaryDTO.getTitle(), interimDiaryDTO.getContent(), interimDiaryDTO.getAngryPhaseId(), isPublic);
        diaryService.addInterimDiary(interimDiary, file);

        return new ResponseEntity<>(new ResponseMessage(true, "임시 다이어리 등록 성공", ""), HttpStatus.OK);
    }

    @GetMapping("interim-diaries/{diaryId}")
    public ResponseEntity<ResponseMessage> inquiryInterimDiaryDetail(@PathVariable int diaryId) {
        int memberId = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();

        Map<String, Object> data = diaryService.getInterimDiaryDetail(diaryId, memberId);

        return new ResponseEntity<>(new ResponseDataMessage(true, "임시 다이어리 상세조회 성공", "", data), HttpStatus.OK);
    }

    @GetMapping("interim-diary/{lastDiaryId}/{size}")
    public ResponseEntity<ResponseMessage> inquiryInterimDiaryList(@PathVariable int lastDiaryId, @PathVariable int size) {
        int memberId = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();

        Map<String, Object> data = diaryService.getInterimDiaryList(memberId, lastDiaryId, size);

        return new ResponseEntity<>(new ResponseDataMessage(true, "임시 다이어리 목록 조회 성공", "", data), HttpStatus.OK);
    }

    @GetMapping("interim-diary-count")
    public ResponseEntity<ResponseMessage> inquiryInterimDiaryCount() {
        int memberId = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();
        Map<String, Object> data = new HashMap<>();
        data.put("count", diaryService.getInterimDiaryCount(memberId));

        return new ResponseEntity<>(new ResponseDataMessage(true, "임시 다이어리 개수 조회 성공", "", data), HttpStatus.OK);
    }

    @DeleteMapping("interim-diaries/{diaryId}")
    public ResponseEntity<ResponseMessage> removeInterimDiary(@PathVariable int diaryId) {
        int memberId = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();

        diaryService.removeInterimDiary(diaryId, memberId);

        return new ResponseEntity<>(new ResponseMessage(true, "임시 다이어리 삭제 성공", ""), HttpStatus.OK);
    }

    @PutMapping("interim-diaries/{diaryId}")
    public ResponseEntity<ResponseMessage> modifyInterimDiary(@PathVariable int diaryId,
                                                       @RequestParam("public") boolean isPublic,
                                                       @RequestPart(value = "interimDiary") InterimDiaryDTO interimDiaryDTO,
                                                       @RequestBody MultipartFile[] file) {

        int memberId = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();

        InterimDiaryDTO interimDiary = new InterimDiaryDTO(diaryId, memberId, interimDiaryDTO.getTitle(), interimDiaryDTO.getContent(), interimDiaryDTO.getAngryPhaseId(), isPublic, interimDiaryDTO.getRemovedFileId());
        diaryService.changeInterimDiary(interimDiary, file);

        return new ResponseEntity<>(new ResponseMessage(true, "임시 다이어리 수정 성공", ""), HttpStatus.OK);

    }

    @GetMapping("gallery")
    public ResponseEntity<ResponseDataMessage> gallery(@RequestParam int lastDiaryId, @RequestParam int size) {

        Map<String, Object> data = new HashMap<>();

        data.put("list", diaryService.getGallery(lastDiaryId, size));

        return new ResponseEntity<>(new ResponseDataMessage(true, "갤러리 조회", "", data), HttpStatus.OK);
    }

}
