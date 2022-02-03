package com.example.reyestrHelper.controllers;

import com.example.reyestrHelper.servises.DocumentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@Log4j2
@ShellComponent
public class ShellCommands {

	private DocumentService docService;

	@Autowired
	public ShellCommands(DocumentService docService) {
		this.docService = docService;
	}

	@ShellMethod(value = "search case number", key = {"case"})
	public String getCountOfDocuments(String caseNumber) {
		return String.valueOf(docService.getCountOfDocuments(caseNumber));
	}

	@ShellMethod(value = "synchronization", key = {"sync"})
	public void synchronization(
			String credentialsPass,
			String documentKey,
			String range) {
		log.info("synchronization is processing");

		log.info(String.format("input parameters: " +
				"\n credentialsPass = %s" +
				"\n documentKey = %s" +
				"\n range = %s", credentialsPass, documentKey, range));
		docService.synchronization(credentialsPass, documentKey, range);
	}

}
