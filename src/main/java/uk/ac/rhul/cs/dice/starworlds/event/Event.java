package uk.ac.rhul.cs.dice.starworlds.event;

import java.io.Serializable;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.utils.Identifiable;

/**
 * Interface the represents an occurance in the system. For example, an
 * {@link Action}. {@link Event}s are used extensively in the full version of
 * StarWorlds. <br>
 * 
 * @author Ben Wilkins
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Kostas Stathis
 *
 */
public interface Event extends Serializable, Identifiable {

	/**
	 * Getter for the origin of the {@link Event}
	 * 
	 * @return {@link Event}s origin
	 */
	public Object getOrigin();

}
