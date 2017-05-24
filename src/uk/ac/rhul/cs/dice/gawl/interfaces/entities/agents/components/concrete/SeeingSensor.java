package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.components.concrete;

import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.components.AbstractSensor;

public class SeeingSensor extends AbstractSensor {

	public final static String SEEINGSENSORKEY = "SEEINGSENSOR";

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
