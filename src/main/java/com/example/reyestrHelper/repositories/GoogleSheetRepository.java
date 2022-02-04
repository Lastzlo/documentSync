package com.example.reyestrHelper.repositories;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Log4j2
public class GoogleSheetRepository {


	private String documentKey;
	private String range;

	private Sheets service;

	public List<List<Object>> getValues() {
		try {
			ValueRange response = service.spreadsheets().values()
					.get(documentKey, range)
					.execute();
			log.info("extracted data from Google Sheet");
			return response.getValues();
		} catch (IOException ex) {
			log.error("values from Google Sheet did not load");
			return List.of(Collections.emptyList());
		}
	}

	public void updateRow (List<List<Object>> newValues, String range) {
		ValueRange body = new ValueRange()
				.setValues(newValues);
		try {
			UpdateValuesResponse result = service.spreadsheets().values().update(documentKey, range, body)
					.setValueInputOption("RAW")
					.execute();
			log.info("documents was updated in the Google Sheet");
		} catch (IOException e) {
			log.error("cells were not updated in the Google Sheet");
		}
	}

	private GoogleSheetRepository(
			Credential credential,
			String documentKey,
			String range,
			String applicationName) {
		this.documentKey = documentKey;
		this.range = range;
		this.service = new Sheets.Builder(credential.getTransport(), credential.getJsonFactory(), credential)
				.setApplicationName(applicationName)
				.build();
		log.info("GoogleSheetRepository was created");
	}

	public static GoogleSheetRepository create(
			Credential credential,
			String documentKey,
			String range,
			String applicationName) {
		return new GoogleSheetRepository(credential, documentKey, range, applicationName);
	}

}
