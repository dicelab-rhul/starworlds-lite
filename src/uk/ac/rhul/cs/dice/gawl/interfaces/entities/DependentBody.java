package uk.ac.rhul.cs.dice.gawl.interfaces.entities;

import java.util.Set;

import uk.ac.rhul.cs.dice.gawl.interfaces.common.DependentBodyAppearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Actuator;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Sensor;

/**
 * A subclass of {@link ActiveBody} which implements {@link ObjectInterface}.<br/><br/>
 * 
 * Known direct subclasses: //TODO
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Kostas Stathis
 *
 */
public abstract class DependentBody extends ActiveBody implements ObjectInterface {

	public DependentBody(DependentBodyAppearance appearance, Set<Sensor> sensors, Set<Actuator> actuators) {
		super(appearance, sensors, actuators);
	}
}