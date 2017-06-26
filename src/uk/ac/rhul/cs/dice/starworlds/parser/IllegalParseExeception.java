package uk.ac.rhul.cs.dice.starworlds.parser;

public class IllegalParseExeception extends RuntimeException {

	private static final long serialVersionUID = -7421612436124326900L;

	private String message;

	public IllegalParseExeception(String key, String property) {
		this.message = "Key: " + key + " missing property: " + property;
	}

	public IllegalParseExeception(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
