package com.teamY.angryBox.service;

import com.teamY.angryBox.dto.NotificationDTO;
import com.teamY.angryBox.error.customException.InvalidRequestException;
import com.teamY.angryBox.repository.NotificationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final DiaryService diaryService;

    public int getDiaryIdInNtf(int ntfId) {
        return notificationRepository.selectDiaryIdInNtf(ntfId);
    }

    public void changeNtf(int ntfId) {
        notificationRepository.updateNtf(ntfId);
    }

    public Map<String, Object> getNtfList(int memberId, int lastNtfId, int size) {
        if (lastNtfId == 0) {
            lastNtfId = notificationRepository.selectLastIdInNtf(memberId) + 1;
        }

        Map<String, Object> data = new HashMap<>();
        List<NotificationDTO> ntfList = notificationRepository.selectNftList(memberId, lastNtfId, size);
        data.put("ntfList", ntfList);

        if (lastNtfId == 0) {
            data.put("zero", null);
        }
        return data;
    }

    public int checkNtf(int ntfId, int memberId) {
        if(notificationRepository.checkMemberIdInNtf(ntfId) == 0) {
            throw new InvalidRequestException("알림 id 확인 필요");
        } else if(notificationRepository.checkMemberIdInNtf(ntfId) != memberId) {
            throw new InvalidRequestException("알림 받은 사용자와 로그인한 사용자 불일치");
        } else if(notificationRepository.checkDiaryIdInNtf(ntfId) == 0) {
            return 0;
        } else {
            return 1;
        }
    }

}
