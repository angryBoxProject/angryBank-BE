package com.teamY.angryBox.mapper;

import com.teamY.angryBox.dto.NotificationDTO;
import com.teamY.angryBox.vo.NotificationListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NotificationMapper {

    void sendNtf(NotificationDTO ntfDTO);

    void updateNtf(int checked, int ntfId);

    int selectCountCheckedNtf(int memberId, int checked);

    int selectDiaryIdInNtf(int ntfId);

    List<NotificationListVO> selectNftList(int memberId, int lastNtfId, int size);

    int selectLastIdInNtf(int memberId);

    int checkMemberIdInNtf(int ntfId);
}
