package com.teamY.angryBox.controller;

import com.teamY.angryBox.config.security.oauth.MemberPrincipal;
import com.teamY.angryBox.dto.ResponseDataMessage;
import com.teamY.angryBox.dto.ResponseMessage;
import com.teamY.angryBox.dto.TopDiaryDTO;
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
    public ResponseEntity<ResponseMessage> registerDiary(@RequestParam String title, @RequestParam String content,
                                                         @RequestParam int isPublic, @RequestParam int angryPhaseId,
                                                         @RequestParam int coinBankId, @RequestBody MultipartFile[] file) {

        int memberId = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();
        //int memberId = 1;
        DiaryVO diary = new DiaryVO(memberId, title, content, isPublic, angryPhaseId, coinBankId);

        diaryService.addDiary(diary, file);

        return new ResponseEntity<>(new ResponseMessage(true, "다이어리 등록 성공", ""), HttpStatus.OK);

    }

    @GetMapping("diaries/coinBank/{coinBankId}")
    public ResponseEntity<ResponseDataMessage> inquriyDiaryListCoinBank(@PathVariable int coinBankId, @RequestParam int lastDiaryId, @RequestParam int size) {
        Map<String, Object> data = new HashMap<>();
        int memberId = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();
        //int memberId = 1;
        List<DiaryVO> diaryListInCoinBank = diaryService.getDiaryListInCoinBank(memberId, coinBankId, lastDiaryId, size);

        data.put("diaryListInCoinBank", diaryListInCoinBank);

        return new ResponseEntity<>(new ResponseDataMessage(true, "다이어리 조회(저금통별) 성공", "", data), HttpStatus.OK);
    }

    @GetMapping("diaries/month/{date}")
    public ResponseEntity<ResponseDataMessage> inquriyDiaryListInMonth(@PathVariable String date, @RequestParam int lastDiaryId, @RequestParam int size) {
        Map<String, Object> data = new HashMap<>();
        int memberId = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();
        //int memberId = 1;

        List<DiaryVO> diaryListInMonth = diaryService.getDiaryListInMonth(memberId, date, lastDiaryId, size);
        data.put("diaryListInMonth", diaryListInMonth);

        return new ResponseEntity<>(new ResponseDataMessage(true, "다이어리 조회(월별) 성공", "", data), HttpStatus.OK);
    }

    @GetMapping("diaries/dailyTop/{date}/{lastDiaryId}")
    public ResponseEntity<ResponseDataMessage> inquriyDailyTopDiary(@PathVariable String date, @PathVariable int lastDiaryId) {
        Map<String, Object> data = new HashMap<>();
        int writeYear = Integer.parseInt(date.substring(0, 4));
        int writeMonth = Integer.parseInt(date.substring(5, 7));
        int writeDay = Integer.parseInt(date.substring(8, 10));
        TopDiaryDTO topDiaryDTO = new TopDiaryDTO(writeYear, writeMonth, writeDay, 1, lastDiaryId);

        data.put("dailyTopDiary", diaryService.getDailyTop(topDiaryDTO));

        return new ResponseEntity<>(new ResponseDataMessage(true, "다이어리 조회(TOP) 성공", "", data), HttpStatus.OK);
    }

    @GetMapping("diaries/todayTop/{lastDiaryId}")
    public ResponseEntity<ResponseDataMessage> inquriyTodayTopDiary(@PathVariable int lastDiaryId) {
        Map<String, Object> data = new HashMap<>();

        log.info("lastDiaryId : " + lastDiaryId);
        data.put("todayTopDiary", diaryService.getTodayTop(lastDiaryId));

        return new ResponseEntity<>(new ResponseDataMessage(true, "다이어리 조회(TOP) 성공", "", data), HttpStatus.OK);
    }


    @GetMapping("diaries/{diaryId}")
    public ResponseEntity<ResponseMessage> inquriyDiaryDetail(@PathVariable int diaryId) {
        int memberId = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();
        //int memberId = 1;
        List<DiaryFileVO> diary = diaryService.getDiaryDetail(diaryId, memberId);

        Map<String, Object> data = new LinkedHashMap<>();

        for (int i = 0; i < diary.size(); i++) {
            data.put("diary", diary.get(i).getDiaryVO());
            if (diary.get(i).getFileVO() != null) {
                Map<String, Object> fileInfo = new HashMap<>();
                fileInfo.put("fileLink", "/images/" + diary.get(i).getFileVO().getSystemFileName());
                fileInfo.put("fileNo", diary.get(i).getFileVO().getFileNo());
                fileInfo.put("fileId", diary.get(i).getFileVO().getId());
                data.put("file" + (i + 1) + ": ", fileInfo);
            }
        }

        return new ResponseEntity<>(new ResponseDataMessage(true, "다이어리 상세조회 성공", "", data), HttpStatus.OK);

    }

    @DeleteMapping("diaries/{diaryId}")
    public ResponseEntity<ResponseMessage> removeDiary(@PathVariable int diaryId) {
        int memberId = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();
        //int memberId = 1;

        if (diaryService.getDiaryMemberId(diaryId, memberId) == 0) {
            // 에러 던지는 걸로 수정 필요
            return new ResponseEntity<>(new ResponseMessage(false, "작성자와 삭제자 불일치 혹은 존재하지 않는 다이어리Id", "작성자와 삭제자 불일치 혹은 존재하지 않는 다이어리Id"), HttpStatus.BAD_REQUEST);
        } else {
            diaryService.removeDiary(diaryId, memberId);
            return new ResponseEntity<>(new ResponseMessage(true, "다이어리 삭제 성공", ""), HttpStatus.OK);
        }
    }

    @PutMapping("diaries/{diaryId}")
    public ResponseEntity<ResponseMessage> modifyDiary(@PathVariable int diaryId,
                                                       @RequestParam String title, @RequestParam String content,
                                                       @RequestParam int isPublic, @RequestParam int angryPhaseId,
                                                       @RequestBody MultipartFile[] file, @RequestParam List removedFileId) {

        int memberId = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();
        //int memberId = 1;

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
