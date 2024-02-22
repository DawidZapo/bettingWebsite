package com.bettingwebsite;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource("/application-test.properties")
@SpringBootTest
class BettingWebsiteApplicationTests {

	@Test
	void contextLoads() {
	}

}
