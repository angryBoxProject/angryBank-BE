package com.teamY.angryBox.repository;

import com.teamY.angryBox.dto.NotificationDTO;
import com.teamY.angryBox.mapper.NotificationMapper;
import com.teamY.angryBox.vo.NotificationListVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Repository
public class NotificationRepository {

    private final NotificationMapper notificationMapper;

    public void sendNtf(NotificationDTO ntfDTO) {
        ntfDTO.setContent("[" + ntfDTO.getDiaryTitle() + "] 가 토닥토닥을 받았습니다.");
        notificationMapper.sendNtf(ntfDTO);
    }

    public void updateNtf(int ntfId) {
        notificationMapper.updateNtf(1, ntfId);
    }

    public int selectCountCheckedNtf(int memberId, int checked) {
        return notificationMapper.selectCountCheckedNtf(memberId, checked);
    }

    public int selectDiaryIdInNtf(int ntfId){
        return notificationMapper.selectDiaryIdInNtf(ntfId);
    }

    public List<NotificationListVO> selectNftList(int memberId, int lastNtfId, int size) {
        return notificationMapper.selectNftList(memberId, lastNtfId, size);
    }

    public int selectLastIdInNtf(int memberId) {
        return notificationMapper.selectLastIdInNtf(memberId);
    }

    public int checkMemberIdInNtf(int ntfId) {
        return notificationMapper.checkMemberIdInNtf(ntfId);
    }
}
