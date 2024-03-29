package com.teamY.angryBox.controller;

import com.teamY.angryBox.config.security.oauth.MemberPrincipal;
import com.teamY.angryBox.dto.ResponseDataMessage;
import com.teamY.angryBox.dto.ResponseMessage;
import com.teamY.angryBox.service.DiaryService;
import com.teamY.angryBox.service.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequestMapping("notification/")
@AllArgsConstructor
@RestController
public class NotificationController {

    private final NotificationService notificationService;
    private final DiaryService diaryService;

    @GetMapping("{notificationId}")
    public ResponseEntity<ResponseMessage> inquiryNotification(@PathVariable int notificationId) {
        int diaryId = notificationService.getDiaryIdInNtf(notificationId);
        int memberId = ((MemberPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();

        notificationService.changeNtf(notificationId);
        notificationService.checkNtf(notificationId, memberId);

        Map<String, Object> data = diaryService.getDiaryDetail(diaryId, memberId);

        return new ResponseEntity<>(new ResponseDataMessage(true, "알림을 통한 다이어리 상세조회 성공", "", data), HttpStatus.OK);
    }

    @GetMapping("un-checked")
    public ResponseEntity<ResponseMessage> inquiryUnCheckedNotification() {
        int memberId = ((MemberPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();

        Map<String, Object> data = notificationService.getCountUnCheckedNft(memberId, 0);

        return new ResponseEntity<>(new ResponseDataMessage(true, "미확인 알람 개수 확인 성공", "", data), HttpStatus.OK);
    }

    @GetMapping("{lastNotificationId}/{size}")
    public ResponseEntity<ResponseMessage> inquiryNotificationList(@PathVariable int lastNotificationId, @PathVariable int size) {
        int memberId = ((MemberPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();
        Map<String, Object> data =  notificationService.getNtfList(memberId, lastNotificationId, size);
        return new ResponseEntity<>(new ResponseDataMessage(true, "알림 목록 조회 성공", "", data), HttpStatus.OK);
    }

}
