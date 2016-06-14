package uk.ac.rhul.cs.dice.gawl.interfaces.observer;

@FunctionalInterface
public interface CustomObserver {
	public void update(CustomObservable o, Object arg);
}