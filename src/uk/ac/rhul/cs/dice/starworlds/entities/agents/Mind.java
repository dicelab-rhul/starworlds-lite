package uk.ac.rhul.cs.dice.starworlds.entities.agents;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.entities.Agent;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Sensor;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;

/**
 * The interface for minds. it extends {@link CustomObserver}.<br/>
 * <br/>
 * 
 * Known implementations: {@link AbstractAgentMind}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public interface Mind {

	/**
	 * The cycle of all {@link Mind}s. Usually consisting of (but isn't limited
	 * to) {@link Mind#perceive(Object...)}, {@link Mind#decide(Object...)} and
	 * {@link Mind#execute(Object...)} in that order. For an example of
	 * {@link Mind#cycle(Object...)} see
	 * {@link AbstractAgentMind#cycle(Object...)}.
	 * 
	 * @param parameters
	 *            that may be used in the cycle
	 * @return any result of the cycle - often this will be some {@link Action}
	 *         (s)
	 */
	public Object[] cycle(Object... parameters);

	/**
	 * The perceive procedure. Any perceptions received by this {@link Agent}s
	 * {@link Sensor}s should be processed here.
	 * 
	 * @param parameters
	 *            to process, usually some {@link Perception}(s) received by the
	 *            agents {@link Sensor}(s)
	 * @return the processed {@link Perception}
	 */
	public Perception<?> perceive(Object... parameters);

	/**
	 * 
	 * The decide procedure. The agent should come to a decision about what
	 * {@link Action} it would like to attempt.
	 * 
	 * @param parameters
	 *            an array of optional parameters that may be used to make the
	 *            decision
	 * @return the {@link Action} that the agent would like
	 *         to attempt
	 * 
	 */
	public Action decide(Object... parameters);

	/**
	 * The execute procedure. The agent should process the actions it has
	 * decided on and arrive at the action it would like to attempt.
	 * 
	 * @param parameters
	 *            an array of optional parameters that may be used to select the
	 *            action to attempt.
	 * @return
	 */
	public Action execute(Object... parameters);

	/**
	 * Setter for the {@link Agent} that this {@link Mind} resides in.
	 * 
	 * @param body
	 */
	public void setBody(AbstractAgent body);

}