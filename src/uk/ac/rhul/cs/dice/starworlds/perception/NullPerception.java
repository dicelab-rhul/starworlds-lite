package uk.ac.rhul.cs.dice.starworlds.perception;

import java.io.Serializable;

public class NullPerception extends AbstractPerception<Serializable> implements
		Serializable {

	private static final long serialVersionUID = 4623562094859605577L;

	public NullPerception() {
		super(null);
	}
}
