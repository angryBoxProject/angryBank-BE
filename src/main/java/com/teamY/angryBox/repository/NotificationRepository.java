package com.teamY.angryBox.repository;

import com.teamY.angryBox.dto.NotificationDTO;
import com.teamY.angryBox.mapper.NotificationMapper;
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
        //게시글 이름이나 diary_no 에 토닥토닥을 받았습니다. 등으로 수정하면 좋을 듯
        ntfDTO.setContent("[" + ntfDTO.getDiaryTitle() + "] 가 토닥토닥을 받았습니다.");
        notificationMapper.sendNtf(ntfDTO);
    }

    public void updateNtf(int ntfId) {
        notificationMapper.updateNtf(1, ntfId);
    }

    public int selectDiaryIdInNtf(int ntfId){
        return notificationMapper.selectDiaryIdInNtf(ntfId);
    }

    public List<NotificationDTO> selectNftList(int memberId, int lastNtfId, int size) {
        return notificationMapper.selectNftList(memberId, lastNtfId, size);
    }

    public int selectLastIdInNtf(int memberId) {
        return notificationMapper.selectLastIdInNtf(memberId);
    }

    public int checkDiaryIdInNtf(int ntfId) {
        return notificationMapper.checkDiaryIdInNtf(ntfId);
    }

    public int checkMemberIdInNtf(int ntfId) {
        return notificationMapper.checkMemberIdInNtf(ntfId);
    }
}
