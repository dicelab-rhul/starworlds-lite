package uk.ac.rhul.cs.dice.starworlds.entities.agent.goalsplans;

import java.util.function.Consumer;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;

public interface GoalCollection<G extends Goal<A>, A extends Action> {

	public void forEach(Consumer<? super G> con);

	public boolean hasGoals();

	public void clearGoals();

	public boolean containsGoal(G goal);

	public int size();
}
