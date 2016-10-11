package uk.ac.rhul.cs.dice.gawl.interfaces.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Custom implementation of the {@link Observable} class. It allows to select the recipients of a notification
 * in a more specific way.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public class CustomObservable {
	List<CustomObserver> observers;
	
	/**
	 * The default constructor.
	 */
	public CustomObservable() {
		this.observers = new ArrayList<>();
	}

	/**
	 * Returns the {@link List} of {@link CustomObserver} instances contained in this {@link CustomObservable}.
	 * 
	 * @return the {@link List} of {@link CustomObserver} instances.
	 */
	public List<CustomObserver> getObservers() {
		return this.observers;
	}
	
	/**
	 * Adds a {@link CustomObserver} to the {@link List} of observers.
	 * 
	 * @param observer : the {@link CustomObserver} to be added to the {@link List} of observers.
	 */
	public void addObserver(CustomObserver observer) {
		this.observers.add(observer);
	}
	
	/**
	 * Notifies all the registered {@link CustomObserver} elements with a <code>null</code> message.
	 */
	public void notifyObservers() {
		notifyObservers(null);
	}
	
	/**
	 * Notifies all the registered {@link CustomObserver} elements with a message.
	 * 
	 * @param arg : the {@link Object} message to send to the {@link CustomObserver} elements.
	 */
	public void notifyObservers(Object arg) {
		for(CustomObserver observer : this.observers) {
			observer.update(this, arg);
		}
	}
	
	/**
	 * Notifies all the registered {@link CustomObserver} elements of a specific class with a message.
	 * 
	 * @param arg : the {@link Object} message to send to the {@link CustomObserver} elements.
	 * @param c : the class the {@link CustomObserver} elements must inherit from for them to be notified.
	 */
	public void notifyObservers(Object arg, Class<?> c) {
		for(CustomObserver observer : this.observers) {
			if(observer.getClass().isAssignableFrom(c)) {
				observer.update(this, arg);
			}
		}
	}
}