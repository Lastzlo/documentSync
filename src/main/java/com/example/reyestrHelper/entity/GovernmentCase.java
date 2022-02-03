package com.example.reyestrHelper.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GovernmentCase {

	private String documentNumber;
	private String documentLink;
	private String caseNumber;
	private String chairmenName;

}
