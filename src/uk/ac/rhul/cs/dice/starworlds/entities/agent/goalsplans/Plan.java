package uk.ac.rhul.cs.dice.starworlds.entities.agent.goalsplans;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;

public interface Plan<A extends Action> {

	public boolean validate(Collection<Perception<?>> perceptions);

	public A nextAction();

	public boolean isComplete();

}
