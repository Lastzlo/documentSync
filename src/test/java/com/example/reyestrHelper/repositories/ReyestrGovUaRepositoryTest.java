package com.example.reyestrHelper.repositories;

import com.example.reyestrHelper.servises.HtmlBuffer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

//@SpringBootTest
class ReyestrGovUaRepositoryTest {

//	@Autowired
//	private ReyestrGovUaRepository reyestrRepo;

	@Test
	void getResponse() {
		String caseNumber = "916/470/20";
		String page = new ReyestrGovUaRepository().loadPageByCaseNumber(caseNumber);

		assertNotNull(page);

		HtmlBuffer.write(page);
	}
}
