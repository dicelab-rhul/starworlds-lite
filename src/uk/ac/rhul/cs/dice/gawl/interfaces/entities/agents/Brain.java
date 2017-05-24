package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Action;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Agent;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.components.Actuator;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.components.Sensor;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Environment;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;
import uk.ac.rhul.cs.dice.gawl.interfaces.perception.Perception;

/**
 * The interface for the brain of the agent. </br> See
 * {@link AbstractAgentBrain} for more information. </br>
 * 
 * Known implementations: {@link AbstractAgentBrain}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public interface Brain {

	/**
	 * Setter for the mind that reside within this {@link Brain}.
	 * 
	 * @param mind
	 */
	public void setMind(AbstractAgentMind mind);

	/**
	 * Setter for the body that this {@link Brain} resides in (i.e. the agent
	 * this {@link Brain} belongs to).
	 * 
	 * @param body
	 */
	public void setBody(AbstractAgent body);

	/**
	 * Getter for the mind that resides within this {@link Brain}.
	 * 
	 * @return the mind
	 */
	public AbstractAgentMind getMind();

	/**
	 * Getter for the body that this {@link Brain} resides in
	 * 
	 * @return the body
	 */
	public AbstractAgent getBody();

	/**
	 * A method that should be called ONCE per cycle by the {@link Physics} that
	 * is managing time - that is, the physics that controls the cycle:
	 * Perceive, Decide, Execute of all agents within some {@link Environment}.
	 * This method will collect all {@link Perception}s that have been received
	 * by this {@link Agent}s {@link Sensor}s in the previous cycle. See
	 * {@link Physics} for more information.
	 */
	public void perceive();

	/**
	 * A method that should be called ONCE per cycle by the {@link Physics} that
	 * is managing time - that is, the physics that controls the cycle:
	 * Perceive, Decide, Execute of all agents within some {@link Environment}.
	 * This method will attempt any {@link Action}s that this {@link Agent}s
	 * {@link Mind} has decided upon (in its {@link Mind#decide()} procedure)
	 * but utilising the agents {@link Actuator}s or {@link Sensor} that are
	 * capable of active perception. See {@link Physics} for more information.
	 */
	public void execute();

}