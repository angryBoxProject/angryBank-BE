package com.teamY.angryBox.controller;

import com.teamY.angryBox.config.security.oauth.MemberPrincipal;
import com.teamY.angryBox.dto.ResponseDataMessage;
import com.teamY.angryBox.dto.ResponseMessage;
import com.teamY.angryBox.service.DiaryService;
import com.teamY.angryBox.vo.DiaryFileVO;
import com.teamY.angryBox.vo.DiaryVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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



        if(diaryService.getAngryId(angryPhaseId) == 0) {
            // 에러 던지는 걸로 수정 필요
            return new ResponseEntity<>(new ResponseMessage(false, "분노수치 이름 잘못 들어옴", "분노수치 이름 잘못 들어옴"), HttpStatus.BAD_REQUEST);
        } else {
            int memberId = ((MemberPrincipal )SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();
            DiaryVO diary = new DiaryVO(memberId, title, content, isPublic, angryPhaseId, coinBankId);

            diaryService.addDiary(diary, file);

            return new ResponseEntity<>(new ResponseMessage(true, "다이어리 등록 성공", ""), HttpStatus.OK);
        }
    }

    @GetMapping("diaries/coinBank/{coinBankId}")
    public ResponseEntity<ResponseDataMessage> inquriyDiaryListCoinBank(@PathVariable int coinBankId) {
        Map<String, Object> data = new HashMap<>();
        int memberId = ((MemberPrincipal )SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();
        List<DiaryVO> diaryListInCoinBank = diaryService.getDiaryListInCoinBank(memberId, coinBankId);

        data.put("diaryListInCoinBank", diaryListInCoinBank);

        return new ResponseEntity<>(new ResponseDataMessage(true, "다이어리 조회(저금통별) 성공", "", data), HttpStatus.OK);
    }

    @GetMapping("diaries/month/{dateTime}")
    public ResponseEntity<ResponseDataMessage> inquriyDiaryListInMonth(@PathVariable String dateTime) {
        Map<String, Object> data = new HashMap<>();
        int memberId = ((MemberPrincipal )SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();
        int year = Integer.parseInt(dateTime.substring(0, 4));
        int month = Integer.parseInt(dateTime.substring(5, 7));

        List<DiaryVO> diaryListInMonth = diaryService.getDiaryListInMonth(memberId, year, month);
        data.put("diaryListInMonth", diaryListInMonth);

        return new ResponseEntity<>(new ResponseDataMessage(true, "다이어리 조회(월별) 성공", "", data), HttpStatus.OK);
    }

    @GetMapping("diaries/{diaryId}")
    public ResponseEntity<ResponseMessage> inquriyDiaryDetail(@PathVariable int diaryId) {
        int memberId = ((MemberPrincipal )SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();
        List<DiaryFileVO> diary = diaryService.getDiaryDetail(diaryId, memberId);
        if(diary == null) {
            // 에러 던지는 걸로 수정 필요
            return new ResponseEntity<>(new ResponseMessage(false, "비공개 글 혹은 잘못된 다이어리 번호(수정예정)", "비공개 글 혹은 잘못된 다이어리 번호(수정예정)"), HttpStatus.BAD_REQUEST);
        } else {
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
    }

    //TOP글, 대나무숲 에도 반영 필요
    @DeleteMapping("diaries/{diaryId}")
    public ResponseEntity<ResponseMessage> removeDiary(@PathVariable int diaryId) {
        int memberId = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();

        if(diaryService.getDiaryMemberId(diaryId, memberId) == 0) {
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
        if(diaryService.getDiaryMemberId(diaryId, memberId) == 0) {
            // 에러 던지는 걸로 수정 필요
            return new ResponseEntity<>(new ResponseMessage(false, "다이어리 작성자와 수정 요청자 불일치", "다이어리 작성자와 수정 요청자 불일치"), HttpStatus.BAD_REQUEST);
        } else {
            DiaryVO diaryVO = new DiaryVO(diaryId, title, content, isPublic, angryPhaseId);
            diaryService.changeDiary(diaryVO, file, removedFileId);
            return new ResponseEntity<>(new ResponseMessage(true, "다이어리 수정 성공", ""), HttpStatus.OK);
        }
    }





}
