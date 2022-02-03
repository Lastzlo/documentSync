package com.example.reyestrHelper.servises;

import com.example.reyestrHelper.entity.GovernmentCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class HtmlParserTest {

	@Autowired
	private HtmlParser htmlParser;

	@Test
	void getGovernmentCases() {
		String html = HtmlBuffer.read();
		String caseNumber = "916/470/20";

		Set<GovernmentCase> governmentCases = htmlParser.getGovernmentCases(html, caseNumber);
	}

	@Test
	void getCountOfCases() {
		String html = HtmlBuffer.read();
		String caseNumber = "916/470/20";
		String caseNumber2 = "1916/470/2012";

		assertEquals(5, htmlParser.getCountOfDocumentsByCaseNumber(html, caseNumber));
		assertEquals(2, htmlParser.getCountOfDocumentsByCaseNumber(html, caseNumber2));
	}
}
