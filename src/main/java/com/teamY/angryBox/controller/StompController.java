package com.teamY.angryBox.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StompController {
    private final SimpMessagingTemplate template; // 특정 broker 로 메세지 전달

    @MessageMapping(value = "topic/bamboo")
    public void bambooPublish() {
        template.convertAndSend("/sub/topic/bamboo", "hellooooo");
    }
}
