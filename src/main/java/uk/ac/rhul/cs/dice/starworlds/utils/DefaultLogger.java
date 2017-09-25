package uk.ac.rhul.cs.dice.starworlds.utils;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class DefaultLogger {

	private static Logger logger = Logger.getAnonymousLogger();
	static {
		try {
			FileHandler fh = new FileHandler("res/Log.log");
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
	}

	public static void log(String message) {
		logger.info(message);
	}

	public static void main(String[] args) {
		DefaultLogger.log("test");
	}
}
