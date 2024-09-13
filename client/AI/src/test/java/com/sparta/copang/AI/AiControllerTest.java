package com.sparta.copang.AI;

import com.sparta.copang.AI.application.service.AIService;
import com.sparta.copang.AI.presentation.controller.AIController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
		controllers = {AIController.class}
)
class AiControllerTest {
	private MockMvc mvc;

	private Principal mockPrincipal;

	@Autowired
	private WebApplicationContext context;

	@MockBean
	AIService service;

	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context)
				.apply(springSecurity(new MockSpringSecurityFilter()))
				.build();
	}

	private void mockUserSetup() {
		// Mock 테스트 유저 생성
		String username = "gichan";
		String password = "1234";
		String role = "HUBMANAGER";
		UserDetails testUserDetails = new UserDetails() {
			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role);
				return List.of(simpleGrantedAuthority);
			}

			@Override
			public String getPassword() {
				return password;
			}

			@Override
			public String getUsername() {
				return username;
			}
		};
		mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "", testUserDetails.getAuthorities());
	}


	@Test
	void AITest() throws Exception {

		// given
		this.mockUserSetup();
		String request = "너는 Gemini 이니?";


		// when - then
		mvc.perform(post("/api/ai")
				.content(request)
				.contentType(MediaType.TEXT_PLAIN)
				.accept(MediaType.APPLICATION_JSON)
						.principal(mockPrincipal)
		)
				.andExpect(status().isOk())
				.andDo(print());
	}

}
