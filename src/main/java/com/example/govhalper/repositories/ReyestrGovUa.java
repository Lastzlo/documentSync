package com.example.govhalper.repositories;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class ReyestrGovUa {

	public HttpResponse<String> getResponse() {
		return Unirest.post("https://reyestr.court.gov.ua/")
				.header("Cookie", "ASP.NET_SessionId=wb5m5ecwvgqhta54qjoo1ujd; cookiesession1=2BA3B5FCA2BY8QFMOXSZG0PB4ZPJ308A")
				.header("Connection", "keep-alive")
				.field("CaseNumber", "916/470/20")
				.field("PagingInfo.ItemsPerPage", "1000")
				.asString();
	}

}
