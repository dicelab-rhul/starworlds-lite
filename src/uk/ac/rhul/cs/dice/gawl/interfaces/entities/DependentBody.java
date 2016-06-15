package uk.ac.rhul.cs.dice.gawl.interfaces.entities;

import java.util.Set;

import uk.ac.rhul.cs.dice.gawl.interfaces.appearances.DependentBodyAppearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Actuator;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Sensor;

/**
 * A subclass of {@link ActiveBody} which implements {@link ObjectInterface}.<br/><br/>
 * 
 * Known direct subclasses: none.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class DependentBody extends ActiveBody implements ObjectInterface {

	/**
	 * Constructor with a {@link DependentBodyAppearance}, a {@link Set} of {@link Sensor} instances and
	 * one of {@link Actuator} instances.
	 * 
	 * @param appearance : the {@link DependentBodyAppearance} of the {@link DependentBody}.
	 * @param sensors : a {@link Set} of {@link Sensor} instances.
	 * @param actuators : a {@link Set} of {@link Actuator} instances.
	 */
	public DependentBody(DependentBodyAppearance appearance, Set<Sensor> sensors, Set<Actuator> actuators) {
		super(appearance, sensors, actuators);
	}
}