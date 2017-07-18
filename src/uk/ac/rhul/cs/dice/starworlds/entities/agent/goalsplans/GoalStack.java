package uk.ac.rhul.cs.dice.starworlds.entities.agent.goalsplans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;

public class GoalStack<G extends Goal<A>, A extends Action> extends
		ArrayList<G> {

	private static final long serialVersionUID = -2408263837495740573L;

	public GoalStack() {
	}

	public GoalStack(GoalStack<G, A> stack) {
		super(stack);
	}

	private GoalStack(List<G> stack) {
		super(stack);
	}

	public G pop() {
		return this.remove(this.size() - 1);
	}

	public G peek() {
		return this.get(this.size() - 1);
	}

	public void push(G goal) {
		this.add(goal);
	}

	/**
	 * This method validates the stack of {@link Goal}s given some
	 * {@link Perception}s by calling {@link Goal#validate(Collection)} with the
	 * {@link Perception}s for each {@link Goal}. A goal is removed from this
	 * stack if its validation fails (i.e. returns false).
	 */
	public GoalStack<G, A> validate(Collection<Perception<?>> perceptions) {
		/*
		 * Validate top->bottom each goal, returning the top most goal that is
		 * still achievable
		 */
		Integer lastinvalid = null;
		for (int i = 0; i < this.size(); i++) {
			if (!this.get(i).validate(perceptions)) {
				lastinvalid = i;
				break;
			}
		}
		GoalStack<G, A> toRemove = null;
		if (lastinvalid != null) {
			// remove any invalid goals
			toRemove = new GoalStack<G, A>(this.subList(lastinvalid,
					this.size()));
			this.removeRange(lastinvalid, this.size());
			return toRemove;
		}
		return toRemove;
	}
}
