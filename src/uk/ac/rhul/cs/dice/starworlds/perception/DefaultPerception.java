package uk.ac.rhul.cs.dice.starworlds.perception;

import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.concrete.SeeingSensor;

/**
 * The default concrete class for {@link Perception}s. This class may be used
 * anywhere. Notably - {@link SeeingSensor}s may accept this type of perception.
 * </br> Extends: {@link AbstractPerception} </br> 
 * 
 * @author Ben
 *
 * @param <T>
 */
public class DefaultPerception<T> extends AbstractPerception<T> {

	/**
	 * Constructor.
	 * 
	 * @param content
	 *            of the perception
	 */
	public DefaultPerception(T content) {
		super(content);
	}

}
