package com.example.reyestrHelper.servises;

import com.example.reyestrHelper.entity.SheetGovernmentCase;
import com.example.reyestrHelper.entity.SheetGovernmentCaseStatus;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Log4j2
@Service
public class SheetGovernmentCaseAdapter {

	private DateTimeFormatter formatter;
	private RangeGenerator rangeGenerator;

	@Autowired
	public SheetGovernmentCaseAdapter(DateTimeFormatter formatter) {
		this.formatter = formatter;
	}

	public List<List<Object>> sheetGovernmentCasesToValues (SheetGovernmentCase sheetCase) {
		return List.of(
				Arrays.asList(
						sheetCase.getCaseNumber(),
						sheetCase.getPrevUpdateTime().format(formatter),
						sheetCase.getPrevUpdateCount(),
						sheetCase.getLastUpdateTime().format(formatter),
						sheetCase.getLastUpdateCount(),
						sheetCase.getDifference()
				)
		);
	}

	public List<SheetGovernmentCase> valuesToSheetGovernmentCases(List<List<Object>> values, String range) {
		this.rangeGenerator = new RangeGenerator(range);

		List<SheetGovernmentCase> cases = new ArrayList<>();

		for (int index = 0; index < values.size(); index++) {
			List<Object> row = values.get(index);
			try {
				SheetGovernmentCase sheetGovernmentCase = fromRow(row, index);
				cases.add(sheetGovernmentCase);
			} catch (RuntimeException ex) {
				log.warn(String.format("Row with values: %s have parameters" +
						" that cannot be parsed", row));
				continue;
			}
		}
		log.info(String.format("%d out of %d values converted",
				cases.size(), values.size()));
		return cases;
	}

	private SheetGovernmentCase fromRow(List row, int index) {

		if (row.size() == 1)  return parseAsNewCase(row, index);
		else return parseAsTraceableCase(row, index);
	}

	private SheetGovernmentCase parseAsTraceableCase(List row, int index) {
		String caseNumber = String.valueOf(row.get(0));

		LocalDateTime prevUpdateTime = LocalDateTime.parse(String.valueOf(row.get(1)), formatter);
		int prevUpdateCount = Integer.parseInt(String.valueOf(row.get(2)));

		LocalDateTime lastUpdateTime = LocalDateTime.parse(String.valueOf(row.get(3)), formatter);
		int lastUpdateCount = Integer.parseInt(String.valueOf(row.get(4)));

		int difference = Integer.parseInt(String.valueOf(row.get(5)));

		String range = rangeGenerator.getRangeByIndex(index);

		return SheetGovernmentCase.builder()
				.caseNumber(caseNumber)
				.prevUpdateTime(prevUpdateTime)
				.prevUpdateCount(prevUpdateCount)
				.lastUpdateTime(lastUpdateTime)
				.lastUpdateCount(lastUpdateCount)
				.difference(difference)
				.range(range)
				.status(SheetGovernmentCaseStatus.TRACEABLE)
				.build();
	}

	private SheetGovernmentCase parseAsNewCase(List row, int index) {

		String caseNumber = String.valueOf(row.get(0));

		String range = rangeGenerator.getRangeByIndex(index);

		return SheetGovernmentCase.builder()
				.caseNumber(caseNumber)
				.range(range)
				.status(SheetGovernmentCaseStatus.NEW)
				.build();
	}

	private static class RangeGenerator {

		private int startIndex;
		private String firstLetter;
		private String secondLetter;
		private String sheetName;

		public RangeGenerator(String range) {
			int exclamationMarkIndex = range.lastIndexOf("!");
			int colonIndex = range.lastIndexOf(":");

			this.sheetName = range.substring(0, exclamationMarkIndex + 1);

			this.startIndex = Integer.parseInt(
					range.substring(exclamationMarkIndex + 1, colonIndex)
							.replaceAll("[A-Z]",""));

			this.firstLetter = range.substring(exclamationMarkIndex + 1, colonIndex).replaceAll("[0-9]","");

			this.secondLetter = range.substring(colonIndex + 1).replaceAll("[0-9]","");
		}

		private String getRangeByIndex (int index) {
			int currIndex = this.startIndex + index;
			return new StringBuilder().append(sheetName)
					.append(firstLetter)
					.append(currIndex)
					.append(':')
					.append(secondLetter)
					.append(currIndex)
					.toString();
		}
	}

}
