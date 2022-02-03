package com.example.reyestrHelper.servises;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.SheetsScopes;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

public class GoogleCredentialsFactory {

	private final String tokensDirectoryPath;

	private final String credentialsFilePath;

	public GoogleCredentialsFactory(String credentialsFilePath, String tokensDirectoryPath) {
		this.credentialsFilePath = credentialsFilePath;
		this.tokensDirectoryPath = tokensDirectoryPath;
	}

	public Credential getCredentials() throws IOException, GeneralSecurityException {
		return getCredentials(GoogleNetHttpTransport.newTrustedTransport());
	}

	/**
	 * Creates an authorized Credential object.
	 *
	 * @param HTTP_TRANSPORT The network HTTP Transport.
	 * @return An authorized Credential object.
	 * @throws IOException If the credentials.json file cannot be found.
	 */
	private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
		final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
		/**
		 * Global instance of the scopes required by this quickstart.
		 * If modifying these scopes, delete your previously saved tokens/ folder.
		 */
		final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);

		// Load client secrets.
		File credentialsFile = new File(credentialsFilePath);
		InputStream in = new FileInputStream(credentialsFile);
		if (in == null) {
			throw new FileNotFoundException("Resource not found: " + credentialsFilePath);
		}
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
				.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(tokensDirectoryPath)))
				.setAccessType("offline")
				.build();
		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
		return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
	}

}
