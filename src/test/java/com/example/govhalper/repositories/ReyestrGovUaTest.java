package com.example.govhalper.repositories;

import com.example.govhalper.servises.HtmlBuffer;
import kong.unirest.HttpResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReyestrGovUaTest {

	@Test
	void getResponse() {
		HttpResponse<String> response = new ReyestrGovUa().getResponse();

		assertTrue(response.isSuccess());
		assertNotNull(response.getBody());

		HtmlBuffer.write(response.getBody());
	}
}
