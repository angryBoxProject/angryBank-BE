package com.teamY.angryBox.controller;

import com.teamY.angryBox.config.security.oauth.MemberPrincipal;
import com.teamY.angryBox.dto.DiaryDTO;
import com.teamY.angryBox.dto.FilterDTO;
import com.teamY.angryBox.dto.ResponseDataMessage;
import com.teamY.angryBox.dto.ResponseMessage;
import com.teamY.angryBox.service.DiaryService;
import com.teamY.angryBox.vo.InterimDiaryVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class DiaryController {

    private final DiaryService diaryService;


    @PostMapping("diary")
    public ResponseEntity<ResponseMessage> createDiary(@RequestParam("public") boolean isPublic,
                                                       @RequestPart(value = "diaryDTO") DiaryDTO diaryDTO,
                                                       @RequestPart(value = "file", required = false) MultipartFile[] file) {

        //log.info("뭐가 또 맘에 안 드는데.. " + diaryDTO.isOpen());

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

        if(data.containsKey("null")) {
            data.remove("null");
            return new ResponseEntity<>(new ResponseDataMessage(true, "다이어리 조회(적금별) 성공(해당 적금에 작성한 다이어리 없음)", "", data), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseDataMessage(true, "다이어리 조회(적금별) 성공", "", data), HttpStatus.OK);
        }
    }

    @GetMapping("diaries/month/{date}/{lastDiaryId}/{size}")
    public ResponseEntity<ResponseDataMessage> inquiryDiaryListInMonth(@PathVariable String date, @PathVariable int lastDiaryId, @PathVariable int size) {

        int memberId = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();
        Map<String, Object> data = diaryService.getDiaryListInMonth(memberId, date, lastDiaryId, size);

        if(data.containsKey("null")) {
            data.remove("null");
            return new ResponseEntity<>(new ResponseDataMessage(true, "다이어리 조회(월별) 성공(해당 월에 작성한 다이어리 없음)", "", data), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseDataMessage(true, "다이어리 조회(월별) 성공", "", data), HttpStatus.OK);
        }
    }

    @GetMapping("diaries/dailyTop/{date}/{lastDiaryId}/{size}")
    public ResponseEntity<ResponseDataMessage> inquiryDailyTopDiary(@PathVariable String date, @PathVariable int lastDiaryId, @PathVariable int size) {

        Map<String, Object> data = diaryService.getDailyTop(date, lastDiaryId, size);

        if(data.containsKey("null")) {
            data.remove("null");
            return new ResponseEntity<>(new ResponseDataMessage(true, "다이어리 조회(Daily TOP) 성공(해당 날짜에 TOP 다이어리 없음)", "", data), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseDataMessage(true, "다이어리 조회(Daily TOP) 성공", "", data), HttpStatus.OK);
        }
    }

    @GetMapping("diaries/todayTop/{lastDiaryId}/{size}")
    public ResponseEntity<ResponseDataMessage> inquiryTodayTopDiary(@PathVariable int lastDiaryId, @PathVariable int size) {
        Map<String, Object> data = diaryService.getTodayTop(lastDiaryId, size);

        if(data.containsKey("null")) {
            data.remove("null");
            return new ResponseEntity<>(new ResponseDataMessage(true, "다이어리 조회(Today TOP) 성공(금일 TOP 다이어리 없음)", "", data), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseDataMessage(true, "다이어리 조회(Today TOP) 성공", "", data), HttpStatus.OK);
        }
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
                                                       @RequestPart(value = "diaryDTO") DiaryDTO diaryDTO,
                                                       @RequestPart(value = "file", required = false) MultipartFile[] file) {

        int memberId = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();

        DiaryDTO diary = new DiaryDTO(diaryId, memberId, diaryDTO.getTitle(), diaryDTO.getContent(), diaryDTO.getAngryPhaseId(), isPublic, diaryDTO.getCoinBankId(), diaryDTO.getRemovedFileId(), diaryDTO.getInterimId());
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
    @GetMapping("diaries")
    public ResponseEntity<ResponseDataMessage> diaries(@RequestParam int lastDiaryId, @RequestParam int size
            , @RequestParam String startDate, @RequestParam String endDate, @RequestParam int angry[], @RequestParam int imageFilter) {

        FilterDTO filter = new FilterDTO(startDate, endDate, angry, imageFilter);
        log.info("filter : " + filter.toString());
        Map<String, Object> data = new HashMap<>();
        data.put("diaries", diaryService.bambooGrove(lastDiaryId, size, filter));
        return new ResponseEntity<ResponseDataMessage>(new ResponseDataMessage(true, "대나무숲 조회 성공", "", data), HttpStatus.OK);
    }

    @PostMapping("/interim-diary")
    public ResponseEntity<ResponseMessage> createInterimDiary(@RequestParam String title, @RequestParam String content,
                                                       @RequestParam("public") boolean isPublic, @RequestParam int angryPhaseId,
                                                       @RequestBody MultipartFile[] file) {

        int memberId = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();

        InterimDiaryVO interimDiaryVO = new InterimDiaryVO(memberId, title, content, isPublic, angryPhaseId);

        diaryService.addInterimDiary(interimDiaryVO, file);

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

        if(data.containsKey("null")) {
            data.remove("null");
            return new ResponseEntity<>(new ResponseDataMessage(true, "임시 다이어리 목록 조회 성공(작성한 임시 다이어리 없음)", "", data), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseDataMessage(true, "임시 다이어리 목록 조회 성공", "", data), HttpStatus.OK);
        }
    }

    @GetMapping("interim-diary-count")
    public ResponseEntity<ResponseMessage> inquiryInterimDiaryCount() {
        int memberId = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();

        Map<String, Object> data = new HashMap<>();
        int count = diaryService.getInterimDiaryCount(memberId);
        if(count == 0) {
            return new ResponseEntity<>(new ResponseDataMessage(true, "임시 다이어리 개수 조회 성공(임시 작성한 다이어리 없음)", "", data), HttpStatus.OK);
        } else {
            data.put("count", count);
            return new ResponseEntity<>(new ResponseDataMessage(true, "임시 다이어리 개수 조회 성공", "", data), HttpStatus.OK);
        }


    }

    @DeleteMapping("interim-diaries/{diaryId}")
    public ResponseEntity<ResponseMessage> removeInterimDiary(@PathVariable int diaryId) {
        int memberId = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();

        diaryService.removeInterimDiary(diaryId, memberId);

        return new ResponseEntity<>(new ResponseMessage(true, "임시 다이어리 삭제 성공", ""), HttpStatus.OK);
    }

    @PutMapping("interim-diaries/{diaryId}")
    public ResponseEntity<ResponseMessage> modifyInterimDiary(@PathVariable int diaryId,
                                                       @RequestParam String title, @RequestParam String content,
                                                       @RequestParam("public") boolean isPublic, @RequestParam int angryPhaseId,
                                                       @RequestBody MultipartFile[] file, @RequestParam List removedFileId) {

        int memberId = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();

        InterimDiaryVO interimDiaryVO = new InterimDiaryVO(diaryId, memberId, title, content, isPublic, angryPhaseId);
        diaryService.changeInterimDiary(interimDiaryVO, file, removedFileId);

        return new ResponseEntity<>(new ResponseMessage(true, "임시 다이어리 수정 성공", ""), HttpStatus.OK);

    }

}




//    기존  ------------------------------------------
//    @PostMapping("diary")
//    public ResponseEntity<ResponseMessage> createDiary(@RequestParam String title, @RequestParam String content,
//                                                       @RequestParam("public") boolean isPublic, @RequestParam int angryPhaseId,
//                                                       @RequestBody MultipartFile[] file,
//                                                       @RequestParam int interimId) {
//
//        int memberId = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();
//        DiaryVO diaryVO = new DiaryVO(memberId, title, content, isPublic, angryPhaseId);
//
//        diaryService.addDiary(diaryVO, file);
//
//        if(interimId != 0) {
//            diaryService.removeInterimDiary(interimId, memberId);
//        }
//
//        return new ResponseEntity<>(new ResponseMessage(true, "다이어리 등록 성공", ""), HttpStatus.OK);
//
//    }

//    ModelAttribute ------------------------------------------
//    @PostMapping("diary")
//    public ResponseEntity<ResponseMessage> createDiary(@ModelAttribute DiaryFileDTO diaryFileDTO) {
//
//        log.info("왜또.. " + diaryFileDTO.getDiaryDTO().isPublic());
//
//        int memberId = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();
//
//        diaryService.addDiary(diaryFileDTO, memberId);
//
//        int interimId = diaryFileDTO.getDiaryDTO().getInterimId();
//        if(interimId != 0) {
//            diaryService.removeInterimDiary(interimId, memberId);
//        }
//
//        return new ResponseEntity<>(new ResponseMessage(true, "다이어리 등록 성공", ""), HttpStatus.OK);
//
//    }

//    기존  ------------------------------------------
//    @PutMapping("diaries/{diaryId}")
//    public ResponseEntity<ResponseMessage> modifyDiary(@PathVariable int diaryId,
//                                                       @RequestParam String title, @RequestParam String content,
//                                                       @RequestParam("public") boolean isPublic, @RequestParam int angryPhaseId,
//                                                       @RequestBody MultipartFile[] file, @RequestParam List removedFileId) {
//
//        int memberId = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();
//
//        DiaryVO diaryVO = new DiaryVO(diaryId, memberId, title, content, isPublic, angryPhaseId);
//        diaryService.changeDiary(diaryVO, file, removedFileId);
//
//        return new ResponseEntity<>(new ResponseMessage(true, "다이어리 수정 성공", ""), HttpStatus.OK);
//
//    }
