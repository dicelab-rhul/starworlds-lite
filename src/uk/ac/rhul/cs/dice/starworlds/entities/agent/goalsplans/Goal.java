package uk.ac.rhul.cs.dice.starworlds.entities.agent.goalsplans;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.entities.Agent;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;

/**
 * The abstract class that defines methods for {@link Goal}s. Each should have
 * at least once associated {@link Plan} and/or sub {@link Goal}. Sub
 * {@link Goal}s reside within a {@link Plan}.
 * 
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 * @param <A>
 *            : the generic type of the {@link Action} that will be attempted by
 *            an {@link Agent} to achieve this {@link Goal}.
 */
public abstract class Goal<A extends Action> {

	protected Plan<A> plan;

	/**
	 * This method should check whether this {@link Goal} has been achieved.
	 * 
	 * @return <code>true</code> if this {@link Goal} has been achieved,
	 *         <code>false</code> otherwise
	 */
	public abstract boolean isAchieved();

	/**
	 * This method should be used to update the {@link Goal} with any new or
	 * changed beliefs that the {@link Agent} may have. Generally a
	 * {@link Goal#update(Object...)} method with specific parameters will be
	 * written in the subclass for convenience.
	 * 
	 * @param beliefs
	 */
	public abstract void update(Object[] beliefs);

	/**
	 * This method should check whether this {@link Goal} is still possible
	 * given any new {@link Perception}s, or whether a switch to a different
	 * {@link Goal} should be made. The switch may be done in the agent or in a
	 * super {@link Goal} / {@link Plan}. The call should be recursive if the
	 * {@link Goal} has sub {@link Goal}s.
	 * 
	 * @param perceptions
	 *            : to evaluate from
	 * @return <code>true</code> if this {@link Goal} is possible,
	 *         <code>false</code> otherwise
	 */
	public abstract boolean isPossible(Collection<Perception<?>> perceptions);

	/**
	 * Gets the {@link Plan} for this {@link Goal}. (Which may be null if this
	 * {@link Goal} is completely dependent on its sub {@link Goal}s).
	 * 
	 * @return the {@link Plan}.
	 */
	public Plan<A> getPlan() {
		return this.plan;
	}

	/**
	 * Sets the {@link Plan} for this {@link Goal}.
	 * 
	 * @param plan
	 *            : of this {@link Goal}.
	 */
	protected void setPlan(Plan<A> plan) {
		this.plan = plan;
	}

	/**
	 * Gets the next {@link Action} from this {@link Goal}s {@link Plan}. This
	 * call will be recursive if this {@link Goal} has sub {@link Goal}s. This
	 * is equivalent to {@link Plan#nextAction()}.
	 * 
	 * @return the next {@link Action}
	 */
	public A nextAction() {
		return plan.nextAction();
	}

	/**
	 * Does this {@link Goal} have any sub {@link Goal}s. This is equivalent to
	 * {@link Plan#hasSubGoals()}.
	 * 
	 * @return <code>true</code> if it does, <code>false</code> otherwise.
	 */
	public boolean hasSubGoals() {
		return plan.hasSubGoals();
	}

	public GoalCollection<? extends Goal<A>, A> getSubgoals() {
		return plan.getSubgoals();
	}

	public void setSubgoals(GoalCollection<? extends Goal<A>, A> subgoals) {
		plan.setSubgoals(subgoals);
	}
}
