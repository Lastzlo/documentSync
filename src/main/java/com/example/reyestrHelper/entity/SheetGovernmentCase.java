package com.example.reyestrHelper.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class SheetGovernmentCase {

	private SheetGovernmentCaseStatus status;
	private String range;

	private String caseNumber;

	private LocalDateTime prevUpdateTime;
	private int prevUpdateCount;

	private LocalDateTime lastUpdateTime;
	private int lastUpdateCount;

	private int difference;
}
