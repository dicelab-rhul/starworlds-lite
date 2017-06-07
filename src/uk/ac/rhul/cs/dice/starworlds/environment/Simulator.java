package uk.ac.rhul.cs.dice.starworlds.environment;

import uk.ac.rhul.cs.dice.starworlds.environment.base.Environment;

/**
 * The interface that should be implemented by any class that is able to start a
 * simulation. This will typically be the {@link Environment} that is highest in
 * the {@link Environment} hierarchy i.e. the {@link Universe}.
 * 
 * @author Ben
 *
 */
public interface Simulator {

	/**
	 * Starts an agent simulation.
	 */
	public void simulate();

}
