package uk.ac.rhul.cs.dice.starworlds.test;

import java.io.Serializable;

public class TestPayload implements Serializable {
	String test = "test";

	@Override
	public String toString() {
		return test;
	}
}