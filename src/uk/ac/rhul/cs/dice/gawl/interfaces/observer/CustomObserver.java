package uk.ac.rhul.cs.dice.gawl.interfaces.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * Custom implementation of the {@link Observer} interface. It substitutes the {@link Observable} with
 * a {@link CustomObservable}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
@FunctionalInterface
public interface CustomObserver {
	
	/**
	 * This method manages a notification from a {@link CustomObservable}. The notification is in the form
	 * of an {@link Object}.
	 * 
	 * @param o : the {@link Observable} notification sender.
	 * @param arg : the notification payload in the form of an {@link Object}.
	 */
	public void update(CustomObservable o, Object arg);
}