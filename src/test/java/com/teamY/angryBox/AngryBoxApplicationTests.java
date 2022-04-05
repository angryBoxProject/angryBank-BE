package com.teamY.angryBox;

import com.teamY.angryBox.mapper.TestMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RequiredArgsConstructor
class AngryBoxApplicationTests {

	//private final TestMapper mapper;
	@Autowired
	private TestMapper mapper;

	@Test
	void contextLoads() {
		//System.out.println("테스트 : " + mapper.test(1));
	}

}
