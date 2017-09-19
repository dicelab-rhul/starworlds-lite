package uk.ac.rhul.cs.dice.starworlds.perception;

import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.concrete.SeeingSensor;

/**
 * The default concrete class for {@link Perception}s. This type of
 * {@link Perception} may be used anywhere. Notably - {@link SeeingSensor}s may
 * accept this. </br> Extends: {@link AbstractPerception} </br>
 * 
 * @author Ben
 *
 * @param <T>
 */
public class DefaultPerception<T> extends AbstractPerception<T> {

	private static final long serialVersionUID = -7306217699913529568L;

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
