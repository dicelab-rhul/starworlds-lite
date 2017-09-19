package uk.ac.rhul.cs.dice.starworlds.entities.agent;

import uk.ac.rhul.cs.dice.starworlds.entities.Agent;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.AbstractSensor;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.concrete.PhysicalActuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.concrete.SpeechActuator;

public abstract class AbstractMind<D, E> implements Mind<D, E> {

	private AbstractAgent<D, E> body;

	protected void setDefaultSensor(AbstractSensor sensor) {
		if (sensor != null) {
			this.body.setDefaultSensor(sensor);
		}
	}

	protected void setDefaultSpeechActuator(SpeechActuator actuator) {
		if (actuator != null) {
			this.body.setDefaultSpeechActuator(actuator);
		}
	}

	protected void setDefaultPhysicalActuator(PhysicalActuator actuator) {
		if (actuator != null) {
			this.body.setDefaultPhysicalActuator(actuator);
		}
	}

	@Override
	public final void setBody(AbstractAgent<D, E> body) {
		if (this.body == null) {
			this.body = body;
		}
	}

	protected AbstractAgent<D, E> getBody() {
		return this.body;
	}

	/**
	 * Getter for the id of this {@link Agent}. This method may be used when
	 * communicating as the 'return address'.
	 * 
	 * @return the id
	 */
	protected String getId() {
		return this.body.getId();
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " " + this.getId();
	}

}
