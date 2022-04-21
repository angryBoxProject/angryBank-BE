package com.teamY.angryBox.mapper;

import com.teamY.angryBox.dto.NotificationDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NotificationMapper {

    void sendNotification(NotificationDTO ntfDTO);

    void updateNotification(int verif, int ntfId);

    int selectDiaryIdInNtf(int ntfId);

    List<NotificationDTO> selectNftList(int receiveMemberId);
}
