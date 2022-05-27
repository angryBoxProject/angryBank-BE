package com.teamY.angryBox.controller;

import com.teamY.angryBox.config.security.oauth.MemberPrincipal;
import com.teamY.angryBox.dto.ResponseMessage;
import com.teamY.angryBox.service.DiaryService;
import com.teamY.angryBox.service.TodackService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
public class TodackController {

    private final TodackService todackService;
    private final DiaryService diaryService;

//    @PostMapping("todack")
//    public ResponseEntity<ResponseMessage> upTodackCount(@RequestBody TodackVO todackVO) {
//        int memberId = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();
//        todackService.checkSendTodack(memberId, todackVO,0, 0);
//        todackService.upTodackCount(todackVO);
//
//        return new ResponseEntity<>(new ResponseMessage(true, "토닥 보내기 성공", ""), HttpStatus.OK);
//    }

    @PostMapping("todack/{diaryId}")
    public ResponseEntity<ResponseMessage> upTodackCount(@PathVariable int diaryId) {
        int memberId = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();
        todackService.checkSendTodack(memberId, diaryId, 0);
        todackService.upTodackCount(diaryId, memberId);

        return new ResponseEntity<>(new ResponseMessage(true, "토닥 보내기 성공", ""), HttpStatus.OK);
    }

    @DeleteMapping("/todack/{diaryId}")
    public ResponseEntity<ResponseMessage> downTodackCount(@PathVariable int diaryId) {
        int memberId = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();
        todackService.checkSendTodack(memberId, diaryId, 1);
        todackService.downTodackCount(diaryId, memberId);

        return new ResponseEntity<>(new ResponseMessage(true, "토닥 취소 성공", ""), HttpStatus.OK);
    }
}
