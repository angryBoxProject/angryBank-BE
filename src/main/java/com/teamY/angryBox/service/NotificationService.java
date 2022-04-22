package com.teamY.angryBox.service;

import com.teamY.angryBox.dto.NotificationDTO;
import com.teamY.angryBox.error.customException.InvalidRequestException;
import com.teamY.angryBox.repository.NotificationRepository;
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

    public int getDiaryIdInNft(int ntfId) {
        return notificationRepository.selectDiaryIdInNtf(ntfId);
    }

    public void changeNtf(int ntfId) {
        notificationRepository.updateNtf(ntfId);
    }

    public List<NotificationDTO> getNtfList(int memberId, int lastNtfId, int size) {
        if(lastNtfId == -1) {
            lastNtfId = notificationRepository.selectLastIdInNtf(memberId) + 1;
            if(lastNtfId == -1) {
                throw new InvalidRequestException("알림 목록 없음");
            }
        }
        return notificationRepository.selectNftList(memberId, lastNtfId, size);
    }
}
