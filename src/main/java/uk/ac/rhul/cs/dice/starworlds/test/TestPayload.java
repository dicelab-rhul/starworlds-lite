package uk.ac.rhul.cs.dice.starworlds.test;

import java.io.Serializable;

public class TestPayload implements Serializable {
    private static final long serialVersionUID = 7989436441744576618L;
    private String test = "test";

    @Override
    public String toString() {
	return this.test;
    }
}