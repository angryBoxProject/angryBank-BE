package com.teamY.angryBox.service;

import com.teamY.angryBox.error.customException.InvalidRequestException;
import com.teamY.angryBox.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Service
public class MailService {
    
    private final JavaMailSender mailSender;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Transactional
    public void sendMail(String email) throws AddressException {
//        회원 메일 DB 조회
        if(memberRepository.findByEmail(email) == null) {
            throw new InvalidRequestException("이메일 DB 조회 결과 없음");
        }

        String tempPassword = tempPassword();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom(String.valueOf(new InternetAddress("helloAngryBox@gmail.com")));
        message.setSubject("angryBox 임시 비밀번호 발급");
        message.setText("안녕하세요. " + email + "님." + System.lineSeparator() +
                "본 메일은 비밀번호 변경을 위해 AngryBank에서 발송하는 메일입니다. "  + System.lineSeparator() +
                "귀하의 임시 비밀번호는   " + tempPassword + "  입니다." + System.lineSeparator() +
                "해당 임시 비밀번호로 로그인 후 마이페이지에서 필히 비밀번호를 변경하시기 바랍니다." + System.lineSeparator() +
                "" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "* 비밀번호 입력 시 공백은 제외해주시기 바랍니다." +
                "" + System.lineSeparator() +
                "* 본인이 요청한 메일이 아닌 경우, 즉시 AngryBank에 로그인하여 비밀번호를 변경하시기 바랍니다." +
                "" + System.lineSeparator() +
                "* 본 메일은 발신 전용 메일로 회신되지 않습니다.");

        log.info("message : " + message);

        mailSender.send(message);

        int memberId = memberRepository.findByEmail(email).getId();
        String encodedPassword = bCryptPasswordEncoder.encode(tempPassword);
        memberRepository.updateMemberPassword(memberId, encodedPassword);
    }


    public String tempPassword() {
        int arr[] = {33, 35, 36, 37, 38, 64}; // ! # $ % & @
        int n = 0;

        StringBuffer temp = new StringBuffer();
        Random rnd = new Random();
        for (int i = 0; i < 15; i++) {
            int rIndex = rnd.nextInt(4);
            switch (rIndex) {
                case 0:
                    // a-z
                    temp.append((char) ((rnd.nextInt(26)) + 97));
                    break;
                case 1:
                    // A-Z
                    temp.append((char) ((rnd.nextInt(26)) + 65));
                    break;
                case 2:
                    // 0-9
                    temp.append((rnd.nextInt(10)));
                    break;
                case 3:
                    n = rnd.nextInt(6);
                    temp.append((char) arr[n]);
                    break;
            }
        }
        return String.valueOf(temp);
    }
}
