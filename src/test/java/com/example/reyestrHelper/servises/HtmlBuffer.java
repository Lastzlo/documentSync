package com.example.reyestrHelper.servises;

import java.io.*;


public class HtmlBuffer {

	public static final String FILE_NAME = "request.html";

	public static boolean write(String html) {
		File file = new File(FILE_NAME);
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))){
		bw.write(html);
		} catch (IOException e) {
			System.out.println("An error when write " + FILE_NAME);
			return false;
		}
		return true;
	}

	public static String read() {
		StringBuilder builder = new StringBuilder();
		try (FileReader fl = new FileReader(FILE_NAME);
			 BufferedReader br = new BufferedReader(fl)
		){
			String value;
			while ((value = br.readLine()) != null) {
				builder.append(value).append("\n");
			}
			builder.deleteCharAt(builder.length() - 1);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}

	public static boolean hasBufferFile() {
		return new File(HtmlBuffer.FILE_NAME).exists();
	}
}
