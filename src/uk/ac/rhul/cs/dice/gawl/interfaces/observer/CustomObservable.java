package uk.ac.rhul.cs.dice.gawl.interfaces.observer;

import java.util.List;

public class CustomObservable {
	List<CustomObserver> observers;
	private boolean changed;
	
	public List<CustomObserver> getObservers() {
		return this.observers;
	}
	
	public void addObserver(CustomObserver observer) {
		this.observers.add(observer);
	}
	
	public void setChanged() {
		this.changed = true;
	}
	
	public void notifyObservers() {
		if(this.changed) {
			this.changed = false;
			
			for(CustomObserver observer : this.observers) {
				observer.update(this, null);
			}
		}
	}
	
	public void notifyObservers(Object arg) {
		if(this.changed) {
			this.changed = false;
			
			for(CustomObserver observer : this.observers) {
				observer.update(this, arg);
			}
		}
	}
	
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