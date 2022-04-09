package com.teamY.angryBox;

import com.teamY.angryBox.mapper.MemberMapper;
import com.teamY.angryBox.mapper.TestMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor


class AngryBoxApplicationTests {

	//private final TestMapper mapper;
//	@Autowired
//	private TestMapper mapper;
	@Autowired
	private MemberMapper mapper;

	@Test
	void contextLoads() {
		//System.out.println("테스트 : " + mapper.test(1));

		assertNotNull(mapper);
		System.out.println("멤버 : " + mapper.selectByEmail("1111@naver.com").getEmail());
	}

}
