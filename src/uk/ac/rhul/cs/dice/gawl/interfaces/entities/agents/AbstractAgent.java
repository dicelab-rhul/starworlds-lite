package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents;

import java.util.Set;

import uk.ac.rhul.cs.dice.gawl.interfaces.common.AbstractAgentAppearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.ActiveBody;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Agent;

/**
 * A subclass of {@link ActiveBody} implementing the {@link Agent} interface. It contains a {@link Brain}
 * and a {@link Mind}.<br/><br/>
 * 
 * Known direct subclasses: //TODO
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Kostas Stathis
 *
 */
public abstract class AbstractAgent extends ActiveBody implements Agent {
	private Brain brain;
	private Mind mind;
	
	public AbstractAgent(AbstractAgentAppearance appearance, Set<Sensor> sensors, Set<Actuator> actuators, AbstractAgentMind mind, AbstractAgentBrain brain) {
		super(appearance, sensors, actuators);
		this.brain = brain;
		this.mind = mind;
	}

	public Brain getBrain() {
		return this.brain;
	}

	public void setBrain(Brain brain) {
		this.brain = brain;
	}

	public Mind getMind() {
		return this.mind;
	}

	public void setMind(Mind mind) {
		this.mind = mind;
	}
}