package com.example.reyestrHelper.repositories;

import com.example.reyestrHelper.servises.HtmlBuffer;
import kong.unirest.HttpResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReyestrGovUaTest {

	@Test
	void getResponse() {
		HttpResponse<String> response = new ReyestrGovUa().getResponse();

		assertTrue(response.isSuccess());
		assertNotNull(response.getBody());

		HtmlBuffer.write(response.getBody());
	}
}
