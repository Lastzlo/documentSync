package com.example.reyestrHelper.configurations;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.sheets.v4.Sheets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class GoogleSheetConfiguration {

	@Value("${googleSheets.APPLICATION_NAME}")
	private final String APPLICATION_NAME = "Google Sheets API Java Quickstart";

	@Bean
	public Sheets getSheets (Credential credential) {
		return new Sheets.Builder(credential.getTransport(), credential.getJsonFactory(), credential)
				.setApplicationName(APPLICATION_NAME)
				.build();
	}

}
