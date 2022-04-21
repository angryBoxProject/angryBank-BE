package com.teamY.angryBox.service;

import com.teamY.angryBox.repository.DiaryRepository;
import com.teamY.angryBox.repository.NotificationRepository;
import com.teamY.angryBox.vo.DiaryFileVO;
import com.teamY.angryBox.vo.DiaryVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final DiaryService diaryService;

    public void getNotification(int ntfId) {
        notificationRepository.updateNotification(ntfId);
    }

    public int getDiaryIdInNft(int ntfId) {
        return notificationRepository.selectDiaryIdInNtf(ntfId);
    }
}
