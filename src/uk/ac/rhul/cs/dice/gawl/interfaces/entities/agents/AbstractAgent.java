package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents;

import java.util.List;

import uk.ac.rhul.cs.dice.gawl.interfaces.appearances.AbstractAppearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.ActiveBody;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Agent;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.components.Actuator;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.components.Sensor;

/**
 * A subclass of {@link ActiveBody} implementing the {@link Agent} interface. It
 * contains a {@link Brain} and a {@link Mind}.<br/>
 * <br/>
 * 
 * Known direct subclasses: {@link AutonomousAgent}, {@link Avatar}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class AbstractAgent extends ActiveBody implements Agent {

	private AbstractAgentBrain brain;
	private AbstractAgentMind mind;

	/**
	 * The class constructor.
	 * 
	 * @param appearance
	 *            : the {@link AbstractAgentAppearance}.
	 * @param sensors
	 *            : a {@link List} of {@link Sensor} instances.
	 * @param actuators
	 *            : a {@link List} of {@link Actuator} instances.
	 * @param mind
	 *            : the {@link AbstractAgentMind}.
	 * @param brain
	 *            : the {@link AbstractAgentBrain}.
	 */
	public AbstractAgent(AbstractAppearance appearance, List<Sensor> sensors,
			List<Actuator> actuators, AbstractAgentMind mind,
			AbstractAgentBrain brain) {
		super(appearance, sensors, actuators);
		this.brain = brain;
		this.brain.setBody(this);
		this.brain.setMind(mind);
		this.mind = mind;
		this.mind.setBrain(brain);
	}

	/**
	 * Returns the {@link Brain} of the {@link AbstractAgent}.
	 * 
	 * @return the {@link Brain} of the {@link AbstractAgent}.
	 */
	@Override
	public AbstractAgentBrain getBrain() {
		return this.brain;
	}

	/**
	 * Returns the {@link Mind} of the {@link AbstractAgent}.
	 * 
	 * @return the {@link Mind} of the {@link AbstractAgent}.
	 */
	@Override
	public AbstractAgentMind getMind() {
		return this.mind;
	}
}