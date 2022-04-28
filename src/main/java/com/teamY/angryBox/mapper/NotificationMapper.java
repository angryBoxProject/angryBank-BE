package com.teamY.angryBox.mapper;

import com.teamY.angryBox.dto.NotificationDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NotificationMapper {

    void sendNtf(NotificationDTO ntfDTO);

    void updateNtf(int checked, int ntfId);

    int selectDiaryIdInNtf(int ntfId);

    List<NotificationDTO> selectNftList(int memberId, int lastNtfId, int size);

    int selectLastIdInNtf(int memberId);

    int checkDiaryIdInNtf(int ntfId);

    int checkMemberIdInNtf(int ntfId);
}
