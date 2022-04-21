package com.teamY.angryBox.repository;

import com.teamY.angryBox.dto.NotificationDTO;
import com.teamY.angryBox.mapper.NotificationMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@AllArgsConstructor
@Repository
public class NotificationRepository {

    private final NotificationMapper notificationMapper;

    public void sendNotification(NotificationDTO ntfDTO) {
        //게시글 이름이나 diary_no 에 토닥토닥을 받았습니다. 등으로 수정하면 좋을 듯
        ntfDTO.setContent(ntfDTO.getDiaryId() + "번 다이어리가 토닥토닥을 받았습니다.");
        notificationMapper.sendNotification(ntfDTO);
    }

    public void updateNotification(int ntfId) {
        notificationMapper.updateNotification(1, ntfId);
    }

    public int selectDiaryIdInNtf(int ntfId){
        return notificationMapper.selectDiaryIdInNtf(ntfId);
    }
}
