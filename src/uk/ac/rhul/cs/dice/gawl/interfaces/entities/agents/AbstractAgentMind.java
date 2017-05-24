package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents;

import java.util.List;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.environmental.CommunicationAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.environmental.SensingAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.speech.AbstractPayload;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.components.Sensor;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.components.concrete.ListeningSensor;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.components.concrete.SeeingSensor;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.components.concrete.SpeechActuator;

/**
 * A {@link Mind} implementation which extends {@link CustomObservable}.<br/>
 * <br/>
 * 
 * Known direct subclasses: {@link AutonomousAgentMind}, {@link AvatarMind}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class AbstractAgentMind implements Mind {

	private AbstractAgentBrain brain;

	@Override
	public final void setBrain(AbstractAgentBrain brain) {
		if (this.brain == null) {
			this.brain = brain;
		}
	}

	protected AbstractAgentBrain getBrain() {
		return this.brain;
	}

	/**
	 * When a mind has made a decision this method should be called with the
	 * action that should be performed.
	 * 
	 * @param action
	 *            to be performed.
	 */
	protected void decision(AbstractEnvironmentalAction action) {
		brain.addActionToPerform(action);
	}

	protected void sense(Class<? extends Sensor> sensor, String... keys) {
		SensingAction sen = new SensingAction(keys);
		Class<?> as = (sensor != null) ? sensor : SeeingSensor.class;
		sen.setActuator(as);
		sen.setSensor(as);
		sen.setActor(this.getBrain().getBody());
		brain.addActionToPerform(sen);
	}

	/**
	 * Communication performed via the agents default {@link SpeechActuator}.
	 * 
	 * @param payload
	 * @param recipientsIds
	 */
	protected <T> void communicate(T payload, List<String> recipientsIds) {
		CommunicationAction<T> com = new CommunicationAction<>(brain.getBody()
				.getId(), recipientsIds, new AbstractPayload<T>(payload));
		com.setActuator(SpeechActuator.class);
		com.setSensor(ListeningSensor.class);
		com.setActor(this.getBrain().getBody());
		brain.addActionToPerform(com);
	}
}