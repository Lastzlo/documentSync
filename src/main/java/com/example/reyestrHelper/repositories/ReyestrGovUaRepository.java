package com.example.reyestrHelper.repositories;

import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class ReyestrGovUaRepository {

	@Value("${reyestrGovUa.url}")
	private String url;

	@Value("${reyestrGovUa.pagingInfo.itemsPerPage}")
	private String itemsPerPage;

	public String loadPageByCaseNumber(String caseNumber) {
		return Unirest.post(url)
				.header("Connection", "keep-alive")
				.field("CaseNumber", caseNumber)
				.field("PagingInfo.ItemsPerPage", itemsPerPage)
				.asString()
				.getBody();
	}


}
