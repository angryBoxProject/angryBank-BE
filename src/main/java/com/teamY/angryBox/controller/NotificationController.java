package com.teamY.angryBox.controller;

import com.teamY.angryBox.config.security.oauth.MemberPrincipal;
import com.teamY.angryBox.dto.ResponseDataMessage;
import com.teamY.angryBox.dto.ResponseMessage;
import com.teamY.angryBox.service.DiaryService;
import com.teamY.angryBox.service.NotificationService;
import com.teamY.angryBox.vo.DiaryFileVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
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
        notificationService.getNotification(notificationId);
        int diaryId = notificationService.getDiaryIdInNft(notificationId);
        Map<String, Object> data = diaryService.getDiaryDetail(diaryId, memberId);

        return new ResponseEntity<>(new ResponseDataMessage(true, "알림을 통한 다이어리 상세조회 성공", "", data), HttpStatus.OK);
    }

}