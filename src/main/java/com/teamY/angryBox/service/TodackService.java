package com.teamY.angryBox.service;

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

    public void upTodackCount(TodackVO todack) {
        todackRepository.upTodackCount(todack);
    }

    public void downTodackCount(TodackVO todack) {
        todackRepository.downTodackCount(todack);
    }
}
