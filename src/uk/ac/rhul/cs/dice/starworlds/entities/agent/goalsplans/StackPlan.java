package uk.ac.rhul.cs.dice.starworlds.entities.agent.goalsplans;

import java.util.ArrayList;
import java.util.List;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;

public abstract class StackPlan<A extends Action> extends Plan<A> {

	protected List<A> actions;

	public StackPlan() {
		this.actions = new ArrayList<>();
	}

	public void pushAction(A action) {
		this.actions.add(action);
	}

	public A peekNextAction() {
		return actions.get(actions.size() - 1);
	}

	@Override
	public A nextAction() {
		return actions.remove(actions.size() - 1);
	}

	@Override
	public boolean isComplete() {
		return this.actions.isEmpty();
	}
}
