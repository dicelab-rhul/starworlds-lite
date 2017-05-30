package uk.ac.rhul.cs.dice.starworlds.actions.environmental;

import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.environment.State;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.Physics;
import uk.ac.rhul.cs.dice.starworlds.perception.AbstractPerception;

/**
 * TODO
 * 
 * Known direct subclasses: none.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class PhysicalAction extends AbstractEnvironmentalAction {

	public abstract Set<AbstractPerception<?>> getAgentPerceptions(Physics physics,
			State context);

	public abstract Set<AbstractPerception<?>> getOthersPerceptions(Physics physics,
			State context);

	public abstract boolean perform(Physics physics, State context);

	public abstract boolean isPossible(Physics physics, State context);

	public abstract boolean verify(Physics physics, State context);

}