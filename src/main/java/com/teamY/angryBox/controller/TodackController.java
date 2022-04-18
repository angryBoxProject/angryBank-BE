package com.teamY.angryBox.controller;

import com.teamY.angryBox.config.security.oauth.MemberPrincipal;
import com.teamY.angryBox.dto.ResponseMessage;
import com.teamY.angryBox.service.DiaryService;
import com.teamY.angryBox.service.TodackService;
import com.teamY.angryBox.vo.TodackVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
public class TodackController {

    private final TodackService todackService;
    private final DiaryService diaryService;


    //이미 보낸 사람이 또 보내지 않도록 유효성검사
    @PostMapping("todack")
    public ResponseEntity<ResponseMessage> upTodackCount(@RequestBody TodackVO todackVO) {
        int memberId = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();
        if(memberId != todackVO.getSendMemberId() || diaryService.getDiaryMemberId(todackVO.getDiaryId(), todackVO.getReceiveMemberId()) == 0) {
            return new ResponseEntity<>(new ResponseMessage(false, "토닥 보내기 실패", ""), HttpStatus.BAD_REQUEST);
        } else {
            TodackVO todack = new TodackVO(todackVO.getDiaryId(), todackVO.getSendMemberId(), todackVO.getReceiveMemberId());
            todackService.upTodackCount(todack);
            return new ResponseEntity<>(new ResponseMessage(true, "토닥 보내기 성공", ""), HttpStatus.OK);
        }
    }
    //이미 취소한 사람이 또 취소하지 않도록 유효성검사
    @DeleteMapping("todack")
    public ResponseEntity<ResponseMessage> downTodackCount(@RequestBody TodackVO todackVO) {
        int memberId = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();
        if(memberId != todackVO.getSendMemberId() || diaryService.getDiaryMemberId(todackVO.getDiaryId(), todackVO.getReceiveMemberId()) == 0) {
            return new ResponseEntity<>(new ResponseMessage(false, "토닥 보내기 실패", ""), HttpStatus.BAD_REQUEST);
        } else {
            TodackVO todack = new TodackVO(todackVO.getDiaryId(), todackVO.getSendMemberId(), todackVO.getReceiveMemberId());
            todackService.downTodackCount(todack);
            return new ResponseEntity<>(new ResponseMessage(true, "토닥 취소 성공", ""), HttpStatus.OK);
        }
    }
}
