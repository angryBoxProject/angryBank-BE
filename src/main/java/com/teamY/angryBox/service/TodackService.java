package com.teamY.angryBox.service;

import com.teamY.angryBox.dto.NotificationDTO;
import com.teamY.angryBox.error.customException.InvalidRequestException;
import com.teamY.angryBox.repository.DiaryRepository;
import com.teamY.angryBox.repository.NotificationRepository;
import com.teamY.angryBox.repository.TodackRepository;
import com.teamY.angryBox.vo.TodackVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class TodackService {

    private final TodackRepository todackRepository;
    private final DiaryRepository diaryRepository;
    private final NotificationRepository notificationRepository;

    public void upTodackCount(int diaryId, int memberId) {
        int receiveMemberId = diaryRepository.selectDiaryMemberId(diaryId);
        TodackVO todackVO = new TodackVO(diaryId, memberId, receiveMemberId);
        todackRepository.upTodackCount(todackVO);

        NotificationDTO ntfDTO = new NotificationDTO(diaryId, memberId, receiveMemberId);
        notificationRepository.sendNtf(ntfDTO);
    }

    public void downTodackCount(int diaryId, int memberId) {
        int receiveMemberId = diaryRepository.selectDiaryMemberId(diaryId);
        TodackVO todackVO = new TodackVO(diaryId, memberId, receiveMemberId);
        todackRepository.downTodackCount(todackVO);
    }

    public void checkSendTodack(int memberId, int diaryId, int todack) {
        if (diaryRepository.checkDiaryId(diaryId) == 0) {
            throw new InvalidRequestException("해당 다이어리 존재하지 않음");
        } else if(diaryRepository.checkIsDeleted(diaryId) == 1) {
            throw new InvalidRequestException("삭제된 다이어리");
        } else if (todack == 0 && todackRepository.checkSendTodack(memberId, diaryId) != 0) { //todack 0 : 토닥 보내기
            throw new InvalidRequestException("해당 게시글에 이미 토닥 보냈음");
        } else if (todack == 1 && todackRepository.checkSendTodack(memberId, diaryId) == 0) { //todack 1 : 토닥 취소하기
            throw new InvalidRequestException("토닥 보낸 적 없거나 이미 취소함");
        } else if (diaryRepository.checkIsPublic(diaryId) == 0 &&
                    diaryRepository.checkDiaryMemberId(diaryId, memberId) == 0) {
            throw new InvalidRequestException("비밀 글이므로 토닥 보내기 불가");
        }

    }


}
