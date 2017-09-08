package uk.ac.rhul.cs.dice.starworlds.entities.agent.goalsplans;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.entities.Agent;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;

/**
 * The abstract class defining methods for a {@link Plan}.
 * 
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 * @param <A>
 *            : the generic type of the {@link Action} that will be attempted by
 *            an {@link Agent} to complete this {@link Plan}.
 */
public abstract class Plan<A extends Action> {

	protected GoalCollection<? extends Goal<A>, A> subgoals;

	/**
	 * Gets the next {@link Action} from this {@link Plan}. This call will be
	 * recursive if this {@link Goal} has sub {@link Goal}s.
	 * 
	 * @return the next {@link Action}
	 */
	public abstract A nextAction();

	/**
	 * Does this {@link Plan} have any more {@link Action}s.
	 * 
	 * @return <code>true</code> if it does, <code>false</code> otherwise
	 */
	public abstract boolean hasActions();

	/**
	 * Does this {@link Plan} hold any sub {@link Goal}s.
	 * 
	 * @return <code>true</code> if it does, <code>false</code> otherwise.
	 */
	public boolean hasSubGoals() {
		return subgoals != null && subgoals.hasGoals();
	}

	/**
	 * This method should check whether this {@link Plan} is complete.
	 * 
	 * @return <code>true</code> if this {@link Plan} is complete,
	 *         <code>false</code> otherwise
	 */
	public abstract boolean isComplete();

	/**
	 * /** This method should be used to update the {@link Plan} with any new or
	 * changed beliefs that the {@link Agent} may have. Generally a
	 * {@link Plan#update(Object...)} method with specific parameters will be
	 * written in the subclass for convenience.
	 * 
	 * @param beliefs
	 */
	public abstract void update(Object[] beliefs);

	/**
	 * This method should check whether this {@link Plan} is still possible
	 * given any new {@link Perception}s, or whether a switch to a different
	 * {@link Goal} / {@link Plan} should be made. The switch may be done in the
	 * agent or in a super {@link Goal} / {@link Plan}. The call should be
	 * recursive if the {@link Goal} has sub {@link Goal}s.
	 * 
	 * @param perceptions
	 *            : to evaluate from
	 * @return <code>true</code> if this {@link Goal} is possible,
	 *         <code>false</code> otherwise
	 */
	public abstract boolean isPossible(Collection<Perception<?>> perceptions);

	public GoalCollection<? extends Goal<A>, A> getSubgoals() {
		return subgoals;
	}

	public void setSubgoals(GoalCollection<? extends Goal<A>, A> subgoals) {
		this.subgoals = subgoals;
	}
}
