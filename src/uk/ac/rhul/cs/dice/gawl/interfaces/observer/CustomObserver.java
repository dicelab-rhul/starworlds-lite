package uk.ac.rhul.cs.dice.gawl.interfaces.observer;

import java.util.Observable;

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