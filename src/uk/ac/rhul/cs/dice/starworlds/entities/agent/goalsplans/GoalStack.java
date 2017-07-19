package uk.ac.rhul.cs.dice.starworlds.entities.agent.goalsplans;

import java.util.ArrayList;
import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;

public abstract class GoalStack<A extends Action> {

	protected ArrayList<Goal<A>> stack;

	public GoalStack() {
		stack = new ArrayList<>();
	}

	public GoalStack(GoalStack<A> stack) {
		this.stack = stack.stack;
	}

	public boolean isEmpty() {
		return stack.isEmpty();
	}

	public Goal<A> pop() {
		return stack.remove(stack.size() - 1);
	}

	public Goal<A> peek() {
		return stack.get(stack.size() - 1);
	}

	public void push(Goal<A> goal) {
		stack.add(goal);
	}

	public abstract GoalStack<A> validate(Collection<Perception<?>> perceptions);
}
