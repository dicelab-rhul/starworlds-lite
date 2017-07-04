package uk.ac.rhul.cs.dice.starworlds.entities.agent;

import java.util.List;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.CommunicationAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.PhysicalAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SensingAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.ActiveBodyAppearance;
import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
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
import uk.ac.rhul.cs.dice.starworlds.parser.DefaultConstructorStore.DefaultConstructor;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;

public abstract class AbstractMindfulActiveBody<D, E> extends ActiveBody {

	protected AbstractMind<D, E> mind;

	/**
	 * Constructor.
	 * 
	 * @param sensors
	 *            : a {@link List} of {@link Sensor} instances.
	 * @param actuators
	 *            : a {@link List} of {@link Actuator} instances.
	 * @param mind
	 *            : the {@link AbstractAgentMind}.
	 * 
	 */
	@DefaultConstructor
	public AbstractMindfulActiveBody(List<Sensor> sensors,
			List<Actuator> actuators, AbstractMind<D, E> mind) {
		super(null, sensors, actuators);
		init(mind);
		this.setAppearance(new ActiveBodyAppearance(this));
	}

	/**
	 * Constructor.
	 * 
	 * @param appearance
	 *            : the {@link ActiveBodyAppearance}.
	 * @param sensors
	 *            : a {@link List} of {@link Sensor} instances.
	 * @param actuators
	 *            : a {@link List} of {@link Actuator} instances.
	 * @param mind
	 *            : the {@link AbstractMind}.
	 */
	public AbstractMindfulActiveBody(ActiveBodyAppearance appearance,
			List<Sensor> sensors, List<Actuator> actuators,
			AbstractMind<D, E> mind) {
		super(appearance, sensors, actuators);
		init(mind);
	}

	private void init(AbstractMind<D, E> mind) {
		this.mind = mind;
		this.mind.setBody(this);
	}

	/**
	 * Returns the {@link AbstractMind} of the {@link AbstractMindfulActiveBody}
	 * .
	 * 
	 * @return the {@link AbstractMind} of the {@link AbstractMindfulActiveBody}
	 *         .
	 */
	public AbstractMind<D, E> getMind() {
		return this.mind;
	}

	protected final Action doAct(AbstractEnvironmentalAction action) {
		return doAct(action, null, null);
	}

	protected final Action doAct(AbstractEnvironmentalAction action,
			Class<? extends AbstractSensor> sensor,
			Class<? extends AbstractActuator> actuator) {
		if (action != null) {
			if (PhysicalAction.class.isAssignableFrom(action.getClass())) {
				Class<? extends PhysicalActuator> physicalActuator = null;
				if (actuator != null) {
					physicalActuator = actuator
							.asSubclass(PhysicalActuator.class);
				}
				return doPhysicalAct((PhysicalAction) action, physicalActuator,
						sensor);
			} else if (SensingAction.class.isAssignableFrom(action.getClass())) {
				return doSenseAct((SensingAction) action, sensor);
			} else if (CommunicationAction.class.isAssignableFrom(action
					.getClass())) {
				return doSpeakAct((CommunicationAction<?>) action, actuator);
			}
			System.err.println("INVALID ACTION: " + action);
			Thread.dumpStack();
			return null;
		}
		return null;
	}

	/**
	 * Attempts active sensing via the specified {@link Sensor}. This
	 * {@link Sensor} (if it is capable) will actively sense the
	 * {@link Environment} that this {@link Agent} currently resides. Any
	 * {@link Perception}s that the {@link Sensor} has received will be
	 * available to the {@link Agent} at the beginning of the next cycle - at
	 * perceive. To attempt to sense some specific information 'keys' must be
	 * used. </br> As an example of a key, an {@link Agent} may want to know
	 * about a specific {@link Agent}, the information received on the agent
	 * will of course depend on the type of {@link Sensor} that is sensing the
	 * agent. i.e. we may listen to the {@link Agent} with a
	 * {@link ListeningSensor}, or look at the {@link Agent} with a
	 * {@link SeeingSensor}. Each of these {@link Action}s will have different
	 * affects. The validity of an active sense will be evaluated by the
	 * {@link Physics} of this {@link Environment} - an {@link Agent} cannot
	 * actively listen for an agent if it doesn't not have permission, or
	 * perhaps if the {@link Agent} is physically too far away. For more
	 * information on keys see {@link Ambient}.
	 * 
	 * @param action
	 *            to attempt
	 * @param sensor
	 *            to use
	 */
	protected final SensingAction doSenseAct(SensingAction action,
			Class<? extends AbstractSensor> sensor) {
		if (action == null) {
			return null;
		}
		Class<?> as = (sensor != null) ? sensor : getDefaultSensorClass();
		action.setActuator(as);
		action.setActor(this.getAppearance());
		this.actuatorActive(this.findSensorByClass(action.getActuator()));
		return action;
	}

	/**
	 * Attempts communication via this agents default {@link SpeechActuator}.
	 * The default {@link SpeechActuator} may be changed using
	 * {@link AbstractAgentMind#setDefaultSpeechActuator(SpeechActuator)}
	 * 
	 */
	protected final CommunicationAction<?> doSpeakAct(
			CommunicationAction<?> action) {
		return doSpeakAct(action, this.getDefaultSpeechActuatorClass());
	}

	/**
	 * Attempts communication via the given {@link Actuator} class. See
	 * {@link CommunicationAction}.
	 * 
	 * @param payload
	 *            of the message to send
	 * @param recipients
	 *            to send to
	 */
	protected final <T> CommunicationAction<?> doSpeakAct(
			CommunicationAction<?> action,
			Class<? extends AbstractActuator> actuator) {
		if (action == null) {
			return null;
		}
		action.setActuator((actuator != null) ? actuator
				: getDefaultSpeechActuatorClass());
		action.setActor(this.getAppearance());
		this.actuatorActive(this.findActuatorByClass(action.getActuator()));
		return action;
	}

	/**
	 * Attempts the {@link PhysicalAction} given. This {@link Action} will be
	 * attempted by the given {@link PhysicalActuator}. This {@link Actuator}
	 * must be capable of attempting the {@link Action} otherwise nothing will
	 * happen. Any {@link Perception}(s) generated as a result of a successful
	 * attempt, the {@link PhysicalAction} will be received by the given
	 * {@link AbstractSensor} assuming it is capable of receiving them, if it is
	 * not, the {@link Perception}(s) will be discarded.
	 * 
	 * @param action
	 *            to attempt
	 * @param actuator
	 *            to attempt the action
	 * @param sensor
	 *            to receive any resulting {@link Perception}s
	 */
	protected final PhysicalAction doPhysicalAct(PhysicalAction action,
			Class<? extends PhysicalActuator> actuator,
			Class<? extends AbstractSensor> sensor) {
		if (action == null) {
			return null;
		}
		action.setActuator((actuator != null) ? actuator
				: getDefaultPhysicalActuatorClass());
		action.setActor(this.getAppearance());
		this.actuatorActive(this.findActuatorByClass(action.getActuator()));
		return action;
	}

	/**
	 * Attempts the {@link PhysicalAction} given. with the default
	 * {@link PhysicalActuator} and {@link AbstractSensor}. These may be changed
	 * with
	 * {@link AbstractAgentMind#setDefaultPhysicalActuator(PhysicalActuator)}
	 * and {@link AbstractAgentMind#setDefaultSensor(AbstractSensor)}
	 * respectively. See
	 * {@link AbstractAgentMind#doPhysicalAct(PhysicalAction, Class, Class)} for
	 * details of this method.
	 * 
	 * @param action
	 *            to attempt
	 */
	protected final PhysicalAction doPhysicalAct(PhysicalAction action) {
		return doPhysicalAct(action, null, null);
	}

	protected Class<? extends SpeechActuator> getDefaultSpeechActuatorClass() {
		Class<? extends SpeechActuator> c = this.getDefaultSpeechActuator()
				.getClass();
		return (c != null) ? c : SpeechActuator.class;
	}

	protected Class<? extends AbstractSensor> getDefaultSensorClass() {
		Class<? extends AbstractSensor> c = this.getDefaultSensor().getClass();
		return (c != null) ? c : SeeingSensor.class;
	}

	protected Class<? extends PhysicalActuator> getDefaultPhysicalActuatorClass() {
		Class<? extends PhysicalActuator> c = this.getDefaultPhysicalActuator()
				.getClass();
		return (c != null) ? c : PhysicalActuator.class;
	}

}
