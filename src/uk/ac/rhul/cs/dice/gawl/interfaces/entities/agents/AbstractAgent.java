package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents;

import java.util.List;

import uk.ac.rhul.cs.dice.gawl.interfaces.appearances.AbstractAgentAppearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.ActiveBody;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Agent;

/**
 * A subclass of {@link ActiveBody} implementing the {@link Agent} interface. It contains a {@link Brain}
 * and a {@link Mind}.<br/><br/>
 * 
 * Known direct subclasses: {@link AutonomousAgent}, {@link Avatar}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class AbstractAgent extends ActiveBody implements Agent {
	private Brain brain;
	private Mind mind;
	
	/**
	 * The class constructor.
	 * 
	 * @param appearance : the {@link AbstractAgentAppearance}.
	 * @param sensors : a {@link List} of {@link Sensor} instances.
	 * @param actuators : a {@link List} of {@link Actuator} instances.
	 * @param mind : the {@link AbstractAgentMind}.
	 * @param brain : the {@link AbstractAgentBrain}.
	 */
	public AbstractAgent(AbstractAgentAppearance appearance, List<Sensor> sensors, List<Actuator> actuators, AbstractAgentMind mind, AbstractAgentBrain brain) {
		super(appearance, sensors, actuators);
		
		this.brain = brain;
		this.mind = mind;
		
		((AbstractAgentBrain) this.brain).addObserver(this.mind);
		((AbstractAgentMind) this.mind).addObserver(this.brain);
		
		((AbstractAgentBrain) this.brain).addObserver(this);
		this.addObserver(this.brain);
	}

	/**
	 * Returns the {@link Brain} of the {@link AbstractAgent}.
	 * 
	 * @return the {@link Brain} of the {@link AbstractAgent}.
	 */
	public Brain getBrain() {
		return this.brain;
	}

	/**
	 * Sets the {@link Brain} of the {@link AbstractAgent}.
	 * 
	 * @param brain : the {@link Brain} of the {@link AbstractAgent}.
	 */
	public void setBrain(Brain brain) {
		this.brain = brain;
	}

	/**
	 * Returns the {@link Mind} of the {@link AbstractAgent}.
	 * 
	 * @return the {@link Mind} of the {@link AbstractAgent}.
	 */
	public Mind getMind() {
		return this.mind;
	}

	/**
	 * Sets the {@link Mind} of the {@link AbstractAgent}.
	 * 
	 * @param mind : the {@link Mind} of the {@link AbstractAgent}.
	 */
	public void setMind(Mind mind) {
		this.mind = mind;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((brain == null) ? 0 : brain.hashCode());
		result = prime * result + ((mind == null) ? 0 : mind.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractAgent other = (AbstractAgent) obj;
		if (brain == null) {
			if (other.brain != null)
				return false;
		} else if (!brain.equals(other.brain))
			return false;
		if (mind == null) {
			if (other.mind != null)
				return false;
		} else if (!mind.equals(other.mind))
			return false;
		return true;
	}
}