package uk.ac.rhul.cs.dice.starworlds.initialisation;

import java.util.UUID;

public final class IDFactory {

	private static final IDFactory instance = new IDFactory();
	private int count;

	private IDFactory() {
	}
	
	public String getNewUUID() {
		return String.valueOf(UUID.randomUUID());
	}

	public String getNewID() {
		return String.valueOf(count++);
		
	}

	public static IDFactory getInstance() {
		return instance;
	}
}
