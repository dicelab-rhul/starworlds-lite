package uk.ac.rhul.cs.dice.starworlds.entities.agent;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.CommunicationAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.PhysicalAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SensingAction;
import uk.ac.rhul.cs.dice.starworlds.entities.Agent;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.AbstractActuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.AbstractSensor;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Actuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Sensor;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.concrete.ListeningSensor;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.concrete.PhysicalActuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.concrete.SeeingSensor;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.concrete.SpeechActuator;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.Ambient;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.Environment;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.Physics;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;

public abstract class AbstractMind<D, E> implements Mind<D, E> {

	private AbstractMindfulActiveBody<D, E> body;

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
	public final void setBody(AbstractMindfulActiveBody<D, E> body) {
		if (this.body == null) {
			this.body = body;
		}
	}

	protected AbstractMindfulActiveBody<D, E> getBody() {
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
