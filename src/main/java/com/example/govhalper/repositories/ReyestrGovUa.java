package com.example.govhalper.repositories;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class ReyestrGovUa {

	private String URL = "https://reyestr.court.gov.ua/";

	public String getData() {
		HttpResponse<String> response = Unirest.post(URL)
				.header("Cookie", "ASP.NET_SessionId=wb5m5ecwvgqhta54qjoo1ujd; cookiesession1=2BA3B5FCA2BY8QFMOXSZG0PB4ZPJ308A")
				.multiPartContent()
				.field("CaseNumber", "916/470/20")
				.field("PagingInfo.ItemsPerPage", "1000")
				.asString();

		return response.getBody();
	}

}
