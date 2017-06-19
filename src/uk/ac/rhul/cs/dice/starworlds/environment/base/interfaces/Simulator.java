package uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces;

/**
 * The interface that should be implemented by a class that is able to start a
 * simulation. This will typically be the {@link Environment} that is highest in
 * the {@link Environment} hierarchy i.e. the {@link Universe}
 * 
 * @author Ben
 *
 */
public interface Simulator extends Runnable {

	/**
	 * Starts a simulation.
	 */
	public void simulate();

	/**
	 * Runs a simulation. Calling run is equivalent to calling
	 * {@link Simulator#simulate()}.
	 */
	@Override
	public default void run() {
		simulate();
	}

}
