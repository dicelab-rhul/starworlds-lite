package uk.ac.rhul.cs.dice.starworlds.parser;

public class IllegalParseExeception extends RuntimeException {

	private static final long serialVersionUID = -7421612436124326900L;

	private String key, missing;

	public IllegalParseExeception(String key, String missing) {
		this.key = key;
		this.missing = missing;
	}

	@Override
	public String getMessage() {
		return "Key: " + key + " missing property: " + missing;
	}
}
