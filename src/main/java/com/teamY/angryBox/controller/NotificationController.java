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
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@AllArgsConstructor
@RestController
public class NotificationController {

    private final NotificationService notificationService;
    private final DiaryService diaryService;

    @GetMapping("notification/{notificationId}")
    public ResponseEntity<ResponseMessage> inquryNotification(@PathVariable int notificationId) {
        int memberId = ((MemberPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();
        notificationService.changeNtf(notificationId);
        Map<String, Object> data = null;

        if(notificationService.checkNtf(notificationId, memberId) == 0) {
            return new ResponseEntity<>(new ResponseDataMessage(true, "알림을 통한 다이어리 상세조회 성공(해당 다이어리 삭제됨)", "", data), HttpStatus.OK);
        } else {

            int diaryId = notificationService.getDiaryIdInNtf(notificationId);
            data = diaryService.getDiaryDetail(diaryId, memberId);

            return new ResponseEntity<>(new ResponseDataMessage(true, "알림을 통한 다이어리 상세조회 성공", "", data), HttpStatus.OK);
        }
    }

    @GetMapping("notification/{lastNotificationId}/{size}")
    public ResponseEntity<ResponseMessage> inquryNotificationList(@PathVariable int lastNotificationId, @PathVariable int size) {
        int memberId = ((MemberPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();
        Map<String, Object> data =  notificationService.getNtfList(memberId, lastNotificationId, size);
        if(data.containsKey("zero")) {
            data.remove("zero");
            return new ResponseEntity<>(new ResponseDataMessage(true, "알림 목록 조회 성공(받은 알림 없음)", "", data), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseDataMessage(true, "알림 목록 조회 성공", "", data), HttpStatus.OK);
        }
    }

}
