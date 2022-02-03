package com.example.reyestrHelper.servises;

import com.example.reyestrHelper.repositories.ReyestrGovUaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {

	private ReyestrGovUaRepository reyestrRepo;

//	private GoogleSheetRepository googleSheetRepository;
	private HtmlParser htmlParser;

	@Autowired
	public DocumentService(ReyestrGovUaRepository reyestrRepo, HtmlParser htmlParser) {
		this.reyestrRepo = reyestrRepo;
		this.htmlParser = htmlParser;
	}

//	@SneakyThrows
//	public void synchronization() {
//		googleSheetRepository = GoogleSheetRepository
//				.create(GoogleConventionalFactory.getCredentials());
//		// Get Connection
//		// start sync
//		//  read sheet and start iteration
//		// for(row){
//		// handleRow(row)
//		// }
//	}

	private void handleRow(Object row) {
//		long lastCount = getCountOfDocuments()
	}

	public long getCountOfDocuments(String caseNumber) {
		String html = reyestrRepo.loadPageByCaseNumber(caseNumber);    // load html

		return htmlParser
				.getCountOfDocumentsByCaseNumber(html, caseNumber);    // parse html and return count of documents
	}

}
