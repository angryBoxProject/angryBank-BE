package com.teamY.angryBox;

import com.teamY.angryBox.config.security.oauth.AuthTokenProvider;
import com.teamY.angryBox.dto.NewCoinBankDTO;
import com.teamY.angryBox.mapper.MemberMapper;
import com.teamY.angryBox.mapper.ProfileMapper;
import com.teamY.angryBox.mapper.TestMapper;
import com.teamY.angryBox.repository.MemberRepository;
import com.teamY.angryBox.service.CoinBankService;
import com.teamY.angryBox.vo.MemberVO;
import com.teamY.angryBox.vo.oauth.KakaoURL;
import com.teamY.angryBox.vo.oauth.OAuthURL;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@RequiredArgsConstructor
class AngryBoxApplicationTests {

	//private final TestMapper mapper;
//	@Autowired
//	private TestMapper mapper;
//	@Autowired
//	private MemberMapper mapper;
	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private AuthTokenProvider authTokenProvider;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private KakaoURL kakaoURL;
	@Disabled
	@Test
	void contextLoads() {
		assertNotNull(authTokenProvider);
	}

	@Disabled
	@Test
	void dbTest(){
		//System.out.println("테스트 : " + mapper.test(1));

		//assertNotNull(mapper);
		//System.out.println("멤버 : " + mapper.selectByEmail("1111@naver.com").getEmail());
		//System.out.println("멤버 !!! : " + memberRepository.selectByEmail("1111@naver.com").getEmail());
		String encodedPassword = bCryptPasswordEncoder.encode("1111");
		//MemberVO member = new MemberVO("2222@naver.com", "2222", encodedPassword);
		//memberRepository.insertMember(member);
	}

	@ToString
	class RoleTest{
		private final String role;
		public RoleTest(String role){
			this.role = role;
		}
	}
	@Disabled
	@Test
	void streamtest(){
		//String role = "ROLE_USER,ROLE_ADMIN";
		String role = "14, 21, 32";
		List<RoleTest> authorities = Arrays.stream(role.split(","))
				.map(RoleTest::new)
				.collect(Collectors.toList());

		System.out.println(authorities);
	}

	@Autowired
	CoinBankService coinBankService;
	@Test
	void profileTest(){
		//MemberVO memberVO = new MemberVO((int) claims.get("id"), (String) claims.get("email"), (String)claims.get("nickname"));
		MemberVO memberVO = new MemberVO(1, "asdf@naver.com", "asdf");
		log.info(memberVO.toString());
	//log.info(profileMapper.selectJoinedProfile(1).toString());

		//coinBankService.createCoinBank( new NewCoinBankDTO( "이름", "메모", 111, "치킨이닭"), 1);
	}
}
