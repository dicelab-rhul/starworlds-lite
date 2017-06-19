package uk.ac.rhul.cs.dice.starworlds.perception;

import java.io.Serializable;

/**
 * A concrete {@link Perception} that has null content. This may be used in
 * cases where an agent is expecting a {@link Perception} (i.e. it is hanging)
 * but there is no {@link Perception} to give (i.e. on the first cycle).
 * 
 * @author Ben
 *
 */
public class NullPerception extends AbstractPerception<Serializable> {

	private static final long serialVersionUID = 4623562094859605577L;

	/**
	 * Constructor.
	 */
	public NullPerception() {
		super(null);
	}
}
