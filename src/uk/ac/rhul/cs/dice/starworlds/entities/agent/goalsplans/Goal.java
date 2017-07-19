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

	public boolean hasSubGoals() {
		return plan.hasSubGoals();
	}

	public Goal<A> popSubGoal() {
		return plan.popSubGoal();
	}

	public Goal<A> peekSubGoal() {
		return plan.peekSubGoal();
	}

	public void pushSubGoal(Goal<A> goal) {
		plan.pushSubGoal(goal);
	}

	public GoalStack<? extends A> getSubgoals() {
		return plan.getSubgoals();
	}

	public void setSubgoals(GoalStack<A> subgoals) {
		this.plan.setSubgoals(subgoals);
	}

}
