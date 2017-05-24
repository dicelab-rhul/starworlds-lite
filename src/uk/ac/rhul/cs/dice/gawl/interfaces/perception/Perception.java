package uk.ac.rhul.cs.dice.gawl.interfaces.perception;

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
 *            the type of the contents of the perception
 */
public interface Perception<T> {

	public T getPerception();

}