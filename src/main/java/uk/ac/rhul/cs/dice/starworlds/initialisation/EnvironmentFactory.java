package uk.ac.rhul.cs.dice.starworlds.initialisation;

/**
 * TODO
 * @author Ben
 *
 */
public class EnvironmentFactory {

	private static EnvironmentFactory instance = new EnvironmentFactory();
	private static IDFactory idfactory = IDFactory.getInstance();

	private EnvironmentFactory() {
	}

	public static EnvironmentFactory getInstance() {
		return instance;
	}
}
