package com.sparta.copang.hub;

import com.sparta.copang.hub.application.dtos.HubDto;
import com.sparta.copang.hub.application.service.HubService;
import com.sparta.copang.hub.application.service.PathService;
import com.sparta.copang.hub.domain.repository.HubRepository;
import com.sparta.copang.hub.presentation.dtos.PathRes;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HubApplicationTests {

	@Autowired
	PathService pathService;
	@Autowired
	HubService hubService;
	@Autowired
	HubRepository hubRepository;

	@Test
	void contextLoads() {
	}

	@Test
	@DisplayName("허브 경로 검색")
	@Transactional
	void test() {

		HubDto hubDto1 = hubService.searchHub("서울").get(0);
		HubDto hubDto2 = hubService.searchHub("부산").get(0);
		PathRes pathRes = pathService.searchPath(hubDto1.getHub_id(), hubDto2.getHub_id());

		Assertions.assertNotNull(pathRes);
		Assertions.assertEquals(pathRes.total_distance(), 3.0);
		Assertions.assertEquals(pathRes.total_duration(), 3.0);
	}

}
