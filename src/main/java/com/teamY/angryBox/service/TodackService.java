package com.teamY.angryBox.service;

import com.teamY.angryBox.error.customException.InvalidRequestException;
import com.teamY.angryBox.repository.DiaryRepository;
import com.teamY.angryBox.repository.TodackRepository;
import com.teamY.angryBox.vo.TodackVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class TodackService {

    private TodackRepository todackRepository;
    private DiaryRepository diaryRepository;

    public void upTodackCount(TodackVO todack) {
        todackRepository.upTodackCount(todack);
    }

    public void downTodackCount(TodackVO todack) {
        todackRepository.downTodackCount(todack);
    }

    public void checkSendTodack(int memberId, TodackVO todackVO, int send, int todack) {
        if(diaryRepository.selectDiaryMemberId(todackVO.getDiaryId(), todackVO.getReceiveMemberId()) == 0) {
            throw new InvalidRequestException("다이어리 작성자와 토닥 받는 사용자 불일치");
        } else if(memberId != todackVO.getSendMemberId()) {
            throw new InvalidRequestException("토닥 보내는 사용자 번호 확인 필요");
        }
        if (todack == 0 && todackRepository.checkSendTodack(memberId, todackVO.getDiaryId(), send) != 0) { //todack 0 : 토닥 보내기
            throw new InvalidRequestException("해당 게시글에 이미 토닥 보냈음");
        } else if (todack == 1 && todackRepository.checkSendTodack(memberId, todackVO.getDiaryId(), send) == 0) { //todack 1 : 토닥 취소하기
            throw new InvalidRequestException("토닥 보낸 적 없거나 이미 취소함");
        }
    }


}
