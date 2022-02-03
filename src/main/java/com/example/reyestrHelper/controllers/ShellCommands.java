package com.example.reyestrHelper.controllers;

import com.example.reyestrHelper.servises.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

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

	@ShellMethod(value = "synchronization")
	public void synchronization() {
	}
}
