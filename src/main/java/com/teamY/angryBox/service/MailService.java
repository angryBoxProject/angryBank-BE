package com.teamY.angryBox.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

@Slf4j
@AllArgsConstructor
@Service
public class MailService {

    @Autowired
    private final JavaMailSender mailSender;

    //메일 보내기
    public void sendMail(String email) throws AddressException {
        log.info("서비스 진입");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom(String.valueOf(new InternetAddress("helloAngryBox@gmail.com")));
        message.setSubject("angryBox 이메일 인증 요청");
        message.setText("안녕하세요. " + email + "님." + System.lineSeparator() +
                "본 메일은 비밀번호 변경을 위해 AngryBank에서 발송하는 메일입니다. "  + System.lineSeparator() +
                "임시 비밀번호는 " + "임시비밀번호!" + "입니다." + System.lineSeparator() +
                "해당 비밀번호로 로그인 후 필히 비밀번호를 변경하시기 바랍니다.");

        log.info("message : " + message);

        mailSender.send(message);
    }

    //임시 비밀번호를 위한 난수 생성

    //메일 발송 후 비밀번호 변경처리



}
