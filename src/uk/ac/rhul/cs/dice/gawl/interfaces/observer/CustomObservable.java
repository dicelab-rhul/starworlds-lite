package uk.ac.rhul.cs.dice.gawl.interfaces.observer;

import java.util.List;

public class CustomObservable {
	List<CustomObserver> observers;
	private boolean changed;
	
	/**
	 * Returns the {@link List} of {@link CustomObserver} instances contained in this {@link CustomObservable}.
	 * 
	 * @return the {@link List} of {@link CustomObserver} instances.
	 */
	public List<CustomObserver> getObservers() {
		return this.observers;
	}
	
	public void addObserver(CustomObserver observer) {
		this.observers.add(observer);
	}
	
	/**
	 * Sets this {@link CustomObservable} as "changed" by switching an internal flag to <code>true</code>.
	 */
	public void setChanged() {
		this.changed = true;
	}
	
	/**
	 * Notifies all the registered {@link CustomObserver} elements with a <code>null</code> message, but
	 * only if <code>setChanged()</code> has been called before.
	 */
	public void notifyObservers() {
		if(this.changed) {
			this.changed = false;
			
			for(CustomObserver observer : this.observers) {
				observer.update(this, null);
			}
		}
	}
	
	/**
	 * Notifies all the registered {@link CustomObserver} elements with a message, but
	 * only if <code>setChanged()</code> has been called before.
	 * 
	 * @param arg : the {@link Object} message to send to the {@link CustomObserver} elements.
	 */
	public void notifyObservers(Object arg) {
		if(this.changed) {
			this.changed = false;
			
			for(CustomObserver observer : this.observers) {
				observer.update(this, arg);
			}
		}
	}
	
	/**
	 * Notifies all the registered {@link CustomObserver} elements of a specific class with a message, but
	 * only if <code>setChanged()</code> has been called before.
	 * 
	 * @param arg : the {@link Object} message to send to the {@link CustomObserver} elements.
	 * @param c : the class the {@link CustomObserver} elements must inherit from for them to be notified.
	 */
	public void notifyObservers(Object arg, Class<?> c) {
		if(this.changed) {
			this.changed = false;
			
			for(CustomObserver observer : this.observers) {
				if(observer.getClass().equals(c.getClass())) {
					observer.update(this, arg);
				}
			}
		}
	}
}