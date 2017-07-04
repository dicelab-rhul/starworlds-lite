package uk.ac.rhul.cs.dice.starworlds.entities.agent;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.entities.Agent;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Sensor;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;

/**
 * 
 * The base interface for a Mind. Generally, a mind takes perceptions as input,
 * decides on an {@link Action} given the {@link Perception}, then executes the
 * decided {@link Action}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 * 
 * @param <D>
 *            type parameter for the {@link Mind#decide(Object)} method.
 * @param <E>
 *            type parameter for the {@link Mind#execute(Object)} method.
 */
public interface Mind<D, E> {

	/**
	 * The cycle of all {@link Mind}s. Usually consisting of (but isn't limited
	 * to) {@link Mind#perceive(Object...)}, {@link Mind#decide(Object...)} and
	 * {@link Mind#execute(Object...)} in that order. For an example of
	 * {@link Mind#cycle(Object...)} see
	 * {@link AbstractAgentMind#cycle(Object...)}.
	 * 
	 * @param perceptions
	 *            : to be used by this {@link Mind}
	 * @return any result(s) of the cycle - often this will be some
	 *         {@link Action}(s)
	 */
	public Object cycle(Collection<Perception<?>> perceptions);

	/**
	 * The perceive procedure. Any perceptions received by this {@link Agent}s
	 * {@link Sensor}s should be processed here.
	 * 
	 * @param perceptions
	 *            : to process
	 * @return the result of perceive (application specific)
	 */
	public Object perceive(Collection<Perception<?>> perceptions);

	/**
	 * 
	 * The decide procedure. The agent should come to a decision about what
	 * {@link Action} it would like to attempt.
	 * 
	 * @param parameters
	 *            an array of optional parameters that may be used to make the
	 *            decision
	 * @return the {@link Action} that the agent would like to attempt
	 * 
	 */
	public Object decide(D parameters);

	/**
	 * The execute procedure. The agent should process the actions it has
	 * decided on and arrive at the action it would like to attempt.
	 * 
	 * @param parameters
	 *            an array of optional parameters that may be used to select the
	 *            action to attempt.
	 * @return
	 */
	public Object execute(E parameters);

	/**
	 * Setter for the {@link AbstractMindfulActiveBody} that this {@link Mind}
	 * resides in.
	 * 
	 * @param body
	 */
	public void setBody(AbstractMindfulActiveBody<D, E> body);

}