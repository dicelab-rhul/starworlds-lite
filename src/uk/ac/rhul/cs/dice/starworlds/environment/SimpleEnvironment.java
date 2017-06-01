package uk.ac.rhul.cs.dice.starworlds.environment;

/**
 * TODO
 * 
 * Known direct subclasses: {@link ComplexEnvironment}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public interface SimpleEnvironment extends Environment {

	public default Boolean isSimple() {
		return true;
	}
}