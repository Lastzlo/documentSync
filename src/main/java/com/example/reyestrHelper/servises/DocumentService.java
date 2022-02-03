package com.example.reyestrHelper.servises;

import com.example.reyestrHelper.entity.SheetGovernmentCase;
import com.example.reyestrHelper.entity.SheetGovernmentCaseStatus;
import com.example.reyestrHelper.repositories.GoogleSheetRepository;
import com.example.reyestrHelper.repositories.ReyestrGovUaRepository;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@Service
public class DocumentService {

	private ReyestrGovUaRepository reyestrRepo;
	private SheetGovernmentCaseAdapter caseAdapter;
	private HtmlParser htmlParser;

	@Value("${googleSheets.tokensDirectoryPath}")
	private String tokensDirectoryPath;

	@Value("${googleSheets.applicationName}")
	private String applicationName;



	@Autowired
	public DocumentService(
			ReyestrGovUaRepository reyestrRepo,
			SheetGovernmentCaseAdapter caseAdapter,
			HtmlParser htmlParser
	) {
		this.reyestrRepo = reyestrRepo;
		this.htmlParser = htmlParser;
		this.caseAdapter = caseAdapter;
	}

	@SneakyThrows
	public void synchronization(
			String credentialsPass,
			String documentKey,
			String range
	) {
		// get Connection
		GoogleCredentialsFactory credentialsFactory = new GoogleCredentialsFactory(credentialsPass, tokensDirectoryPath);
		GoogleSheetRepository googleSheetRepository = GoogleSheetRepository.create(credentialsFactory.getCredentials(), documentKey, range, applicationName);

		// start sync
		List<List<Object>> values = googleSheetRepository.getValues();

		//  read sheet and start iteration
		List<SheetGovernmentCase> cases = caseAdapter.valuesToSheetGovernmentCases(values, range);
		for (SheetGovernmentCase sheetCase : cases) {
			List<List<Object>> lists = handleCase(sheetCase);
			googleSheetRepository.updateRow(lists, sheetCase.getRange());
		}
		log.info("synchronization finished");
	}

	private List<List<Object>> handleCase(SheetGovernmentCase sheetCase) {
		log.info(String.format("case number %s is processing ", sheetCase.getCaseNumber()));
		if (sheetCase.getStatus().equals(SheetGovernmentCaseStatus.NEW)) {
			return handleNewCase(sheetCase);
		} else {
			return handleTraceableCase(sheetCase);
		}
	}

	private List<List<Object>> handleTraceableCase(SheetGovernmentCase sheetCase) {
		int count = getCountOfDocuments(sheetCase.getCaseNumber());

		LocalDateTime dateTime = LocalDateTime.now();

		sheetCase.setPrevUpdateTime(sheetCase.getLastUpdateTime());
		sheetCase.setPrevUpdateCount(sheetCase.getLastUpdateCount());
		sheetCase.setLastUpdateTime(dateTime);
		sheetCase.setLastUpdateCount(count);
		sheetCase.setDifference(count - sheetCase.getPrevUpdateCount());

		return caseAdapter.sheetGovernmentCasesToValues(sheetCase);
	}

	private List<List<Object>> handleNewCase(SheetGovernmentCase sheetCase) {
		int count = getCountOfDocuments(sheetCase.getCaseNumber());

		LocalDateTime dateTime = LocalDateTime.now();

		sheetCase.setPrevUpdateTime(dateTime);
		sheetCase.setPrevUpdateCount(count);
		sheetCase.setLastUpdateTime(dateTime);
		sheetCase.setLastUpdateCount(count);
		sheetCase.setDifference(0);

		return caseAdapter.sheetGovernmentCasesToValues(sheetCase);
	}

	public int getCountOfDocuments(String caseNumber) {
		String html = reyestrRepo.loadPageByCaseNumber(caseNumber);
		log.info("html page by case number was loaded");
		return htmlParser
				.getCountOfDocumentsByCaseNumber(html, caseNumber);
	}

}
