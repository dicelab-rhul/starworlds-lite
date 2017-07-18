package uk.ac.rhul.cs.dice.starworlds.entities.agent.goalsplans;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;

public abstract class Goal<A extends Action> {

	protected Plan<A> plan;

	public boolean validate(Collection<Perception<?>> perceptions) {
		return this.plan.validate(perceptions);
	}

	public boolean isComplete() {
		return plan.isComplete();
	}

	public Plan<A> getPlan() {
		return this.plan;
	}

	public A nextAction() {
		return plan.nextAction();
	}
}
