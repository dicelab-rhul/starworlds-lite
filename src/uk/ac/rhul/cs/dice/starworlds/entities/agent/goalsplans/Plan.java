package uk.ac.rhul.cs.dice.starworlds.entities.agent.goalsplans;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;

public abstract class Plan<A extends Action> {

	protected GoalStack<A> subgoals;

	public abstract boolean validate(Collection<Perception<?>> perceptions);

	public abstract A nextAction();

	public abstract boolean isComplete();

	public boolean hasSubGoals() {
		return !(subgoals == null || subgoals.isEmpty());
	}

	public Goal<A> popSubGoal() {
		return subgoals.pop();
	}

	public Goal<A> peekSubGoal() {
		return subgoals.peek();
	}

	public void pushSubGoal(Goal<A> goal) {
		subgoals.push(goal);
	}

	public GoalStack<A> getSubgoals() {
		return subgoals;
	}

	public void setSubgoals(GoalStack<A> subgoals) {
		this.subgoals = subgoals;
	}

}
