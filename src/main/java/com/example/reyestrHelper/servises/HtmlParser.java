package com.example.reyestrHelper.servises;

import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class HtmlParser {

	public int getCountOfDocumentsByCaseNumber(String html, String caseNumber) {
		int count = (int) Jsoup.parse(html)
				.getElementsByAttributeValueContaining("class", "CaseNumber tr")
				.stream()
				.filter(el -> el.html().equals(caseNumber))
				.count();

		log.info(String.format("found %d documents with case number = %s",
				count, caseNumber));
		return count;
	}
}
