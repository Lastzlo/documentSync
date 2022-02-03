package com.example.reyestrHelper.servises;

import com.example.reyestrHelper.entity.GovernmentCase;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class HtmlParser {

	public long getCountOfDocumentsByCaseNumber(String html, String caseNumber) {
		return getStreamElementsContainingCaseNumber(html, caseNumber)
				.count();
	}

	Stream<Element> getStreamElementsContainingCaseNumber (String html, String caseNumber) {
		return Jsoup.parse(html)
				.getElementsByAttributeValueContaining("class", "CaseNumber tr")
				.stream()
				.filter(el -> el.html().equals(caseNumber));
	}


	public Set<GovernmentCase> getGovernmentCases (String html, String caseNumber) {
		return getStreamElementsContainingCaseNumber(html, caseNumber)
				.map(Element::parent)
				.map(el -> GovermantCaseAdapter.fromElement(el))
				.collect(Collectors.toSet());
	}


	private static class GovermantCaseAdapter {

		@Value("${reyestrGovUa.url}")
		private static String url;

		public static GovernmentCase fromElement(Element el) {
			String documentNumber =
					el.getElementsByAttributeValueContaining("class", "doc_text2")
							.get(0)
							.html();

			String documentLink = url + documentNumber;

			String caseNumber =
					el.getElementsByAttributeValueContaining("class", "CaseNumber tr")
							.get(0)
							.html();

			String chairmenName =
					el.getElementsByAttributeValueContaining("class", "ChairmenName tr")
							.get(0)
							.html();

			return GovernmentCase.builder()
					.documentNumber(documentNumber)
					.documentLink(documentLink)
					.caseNumber(caseNumber)
					.chairmenName(chairmenName)
					.build();
		}

	}
}
