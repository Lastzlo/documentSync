package com.example.govhalper.repositories;

import com.example.govhalper.servises.HtmlBuffer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReyestrGovUaTest {

	@Test
	void getData() {
		String data = new ReyestrGovUa().getData();

		assertNotNull(data);
//		HtmlBuffer.write(data);
	}
}
