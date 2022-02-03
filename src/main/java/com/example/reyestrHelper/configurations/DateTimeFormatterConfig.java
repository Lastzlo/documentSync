package com.example.reyestrHelper.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

@Configuration
public class DateTimeFormatterConfig {

	@Value("${googleSheets.dataPattern}")
	private String dataPattern;

	@Bean
	public DateTimeFormatter formatter() {
		return DateTimeFormatter.ofPattern(dataPattern);
	}

}
