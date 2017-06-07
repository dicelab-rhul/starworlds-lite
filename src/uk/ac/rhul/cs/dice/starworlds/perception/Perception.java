package uk.ac.rhul.cs.dice.starworlds.perception;

/**
 * The interface for perceptions.<br/>
 * <br/>
 * 
 * Known implementations: {@link AbstractPerception}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 * @param <T>
 *            the type of the content of the perception
 */
public interface Perception<T> {

	/**
	 * Getter for the content of this {@link Perception}.
	 * 
	 * @return the content
	 */
	public T getPerception();

}