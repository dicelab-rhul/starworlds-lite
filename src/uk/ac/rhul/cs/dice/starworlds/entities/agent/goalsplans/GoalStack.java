package uk.ac.rhul.cs.dice.starworlds.entities.agent.goalsplans;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;

public class GoalStack<G extends Goal<A>, A extends Action> implements
		GoalCollection<G, A> {

	protected List<G> goals;

	public GoalStack() {
		goals = new ArrayList<>();
	}

	@Override
	public boolean hasGoals() {
		return !(goals == null || goals.isEmpty());
	}

	public boolean containsGoal(G action) {
		return goals.contains(action);
	}

	@Override
	public void clearGoals() {
		goals.clear();
	}

	@Override
	public int size() {
		return goals.size();
	}

	public G nextGoal() {
		return goals.remove(goals.size() - 1);
	}

	public void pushGoal(G action) {
		this.goals.add(action);
	}

	public G peekGoal() {
		return goals.get(goals.size() - 1);
	}

	@Override
	public void forEach(Consumer<? super G> con) {
		goals.forEach(con);
	}
}
