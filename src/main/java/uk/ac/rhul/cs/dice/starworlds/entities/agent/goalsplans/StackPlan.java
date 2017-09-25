package uk.ac.rhul.cs.dice.starworlds.entities.agent.goalsplans;

import java.util.ArrayList;
import java.util.List;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;

public abstract class StackPlan<A extends Action> extends Plan<A> {

	protected List<A> actions;

	public StackPlan() {
		this.actions = new ArrayList<>();
	}

	@Override
	public boolean hasActions() {
		return !(actions == null || actions.isEmpty());
	}

	@Override
	public A nextAction() {
		return actions.remove(actions.size() - 1);
	}

	public boolean containsAction(A action) {
		return actions.contains(action);
	}

	public void pushAction(A action) {
		this.actions.add(action);
	}

	public A peekAction() {
		return actions.get(actions.size() - 1);
	}
}
