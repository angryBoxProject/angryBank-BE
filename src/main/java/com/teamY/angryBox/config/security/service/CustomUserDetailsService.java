package com.teamY.angryBox.config.security.service;

import com.teamY.angryBox.config.security.oauth.MemberPrincipal;
import com.teamY.angryBox.repository.MemberRepository;
import com.teamY.angryBox.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberVO member = memberRepository.findByEmail(username);

        if(member == null)
            throw new UsernameNotFoundException("Can not find username.");

        log.info("memberPrinipal~~~~!!");
        return MemberPrincipal.create(member);
    }
}
