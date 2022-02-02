package com.example.reyestrHelper.servises;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HtmlBufferTest {

	private static final String HTML = "<h1>Some Text</h1>\n" +
			"\t\t\t\t<!-- <h4></h4> -->\n" +
			"\t\t\t\t<div id=\"menu\" class=\"home_page\">\n" +
			"\t\t\t\t\tYou are welcome, !\n" +
			"\t\t\t\t</div>";

	@Test
	void writeAndReadTest() {
		Assertions.assertTrue(HtmlBuffer.write(HTML));
		Assertions.assertTrue(HtmlBuffer.hasBufferFile());
		Assertions.assertEquals(HTML, HtmlBuffer.read());
	}

}
