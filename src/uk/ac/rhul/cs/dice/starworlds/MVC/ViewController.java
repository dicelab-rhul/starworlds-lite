package uk.ac.rhul.cs.dice.starworlds.MVC;

import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.Universe;

/**
 * The interface for the Controller module of the MVC pattern. The
 * {@link ViewController} should be concretely implemented and be used to
 * mediate the interaction between a View and a {@link Universe} (which is the
 * Model). </br> Known subclasses: {@link AbstractViewController}.
 * 
 * @author Ben
 *
 */
public interface ViewController {

	/**
	 * Getter for the {@link Universe} (the Model).
	 * 
	 * @return the {@link Universe}
	 */
	public Universe getUniverse();

	/**
	 * Setter for the {@link Universe} (the Model).
	 * 
	 * @param universe
	 */
	public void setUniverse(Universe universe);
}
