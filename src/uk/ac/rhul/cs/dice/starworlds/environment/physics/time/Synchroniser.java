package uk.ac.rhul.cs.dice.starworlds.environment.physics.time;

public interface Synchroniser {

	public void runActors();

	public void propagateActions();

	public void executeActions();

}
