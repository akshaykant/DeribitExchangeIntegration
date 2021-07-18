package com.akshaykant.coppertest;

import com.akshaykant.coppertest.common.resources.WebConstants;
import com.akshaykant.coppertest.web.UserManagementController;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserManagementController.class)
class CopperTestApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void accountBalanceHappyPath() throws Exception {

		//MockitoAnnotations.initMocks(this);

		String getAccountBalanceURL = WebConstants.API_PATH_PREFIX + "/account/balances";

		ImmutableMap<String, String > headers =
				ImmutableMap.of("client_secret",
						"4_eoDpo_BfSh7Bdg1-BsfOHQqka_hOXnOI_S8YquERc");

		MultiValueMap<String, String> getUserBalanceRequest = new LinkedMultiValueMap<>();

		getUserBalanceRequest.put("client_id", Collections.singletonList("TIYIapc7"));

		mockMvc.perform(MockMvcRequestBuilders
				.get(getAccountBalanceURL, getUserBalanceRequest, headers)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
	}

}
