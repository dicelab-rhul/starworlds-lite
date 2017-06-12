package uk.ac.rhul.cs.dice.starworlds.parser;

import java.io.IOException;

public class TestParser {

	public static void main(String[] args) {
		try {
			Parser p = new Parser(
					"init/structureinit.json");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
