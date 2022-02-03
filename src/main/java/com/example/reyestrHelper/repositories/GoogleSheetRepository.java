package com.example.reyestrHelper.repositories;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.sheets.v4.Sheets;

public class GoogleSheetRepository {

	private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";

	private final Sheets service;


	private GoogleSheetRepository(Credential credential) {
		service = new Sheets.Builder(credential.getTransport(), credential.getJsonFactory(), credential)
				.setApplicationName(APPLICATION_NAME)
				.build();
	}


	public static GoogleSheetRepository create(Credential credential) {
		return new GoogleSheetRepository(credential);
	}



}
