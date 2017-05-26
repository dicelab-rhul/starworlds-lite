package uk.ac.rhul.cs.dice.starworlds.actions.environmental;

import uk.ac.rhul.cs.dice.starworlds.environment.State;
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

	public abstract AbstractPerception<?> perform(State context);

	public abstract boolean isPossible(State context);

	public abstract boolean verify(State context);

}