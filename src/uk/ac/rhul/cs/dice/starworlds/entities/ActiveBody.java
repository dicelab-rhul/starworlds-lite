package uk.ac.rhul.cs.dice.starworlds.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.EnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.AbstractAppearance;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.AbstractSensor;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Actuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Component;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Sensor;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.concrete.PhysicalActuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.concrete.SpeechActuator;
import uk.ac.rhul.cs.dice.starworlds.environment.AbstractEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.Physics;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;

/**
 * A subclass of {@link PhysicalBody} capable to perform an
 * {@link EnvironmentalAction}, thus implementing {@link Actor} It has a
 * {@link List} of {@link Sensor} elements and one of {@link Actuator} elements.<br/>
 * <br/>
 * 
 * Known direct subclasses: {@link AbstractAgent}, {@link DependentBody}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class ActiveBody extends PhysicalBody implements Actor {

	private AbstractSensor defaultSensor;
	private SpeechActuator defaultSpeechActuator;
	private PhysicalActuator defaultPhysicalActuator;

	// When a sensor receives some input, it should tell the body it has
	// received something.
	private Set<Sensor> activatedSensors;
	// When an action is about to be performed by an actuator, let the body know
	private Set<Component> activatedActuators;

	private List<Sensor> sensors;
	private List<Actuator> actuators;
	private AbstractEnvironment environment;

	/**
	 * Constructor with a {@link AbstractAppearance}, a {@link List} of
	 * {@link Sensor} instances and one of {@link Actuator} instances.
	 * 
	 * 
	 * @param appearance
	 *            : the {@link AbstractAppearance} of the {@link ActiveBody}.
	 * @param sensors
	 *            : a {@link List} of {@link Sensor} instances.
	 * @param actuators
	 *            : a {@link List} of {@link Actuator} instances.
	 */
	public ActiveBody(AbstractAppearance appearance, List<Sensor> sensors,
			List<Actuator> actuators) {
		super(appearance);
		sensors = sensors != null ? sensors : new ArrayList<>();
		actuators = actuators != null ? actuators : new ArrayList<>();
		this.sensors = new ArrayList<>();
		this.actuators = new ArrayList<>();
		this.activatedActuators = new HashSet<>();
		this.activatedSensors = new HashSet<>();
		for (Actuator a : actuators) {
			this.addActuator(a);
		}

		for (Sensor s : sensors) {
			this.addSensor(s);
		}

		this.setDefaultSensor((AbstractSensor) findSensorByClass(AbstractSensor.class));
		this.setDefaultSpeechActuator((SpeechActuator) findActuatorByClass(SpeechActuator.class));
		this.setDefaultPhysicalActuator((PhysicalActuator) findActuatorByClass(PhysicalActuator.class));
		System.out.println(this.getDefaultSensor() + ","
				+ this.getDefaultPhysicalActuator() + ","
				+ this.getDefaultSpeechActuator());
	}

	public Sensor findSensorByClass(Class<?> c) {
		return (Sensor) findComponentByClass(c, sensors);
	}

	public Actuator findActuatorByClass(Class<?> c) {
		return (Actuator) findComponentByClass(c, actuators);
	}

	private <T> T findComponentByClass(Class<T> c, List<?> list) {
		for (Object o : list) {
			if (c.isAssignableFrom(o.getClass())) {
				return c.cast(o);
			}
		}
		return null;
	}

	/**
	 * Returns a {@link List} of {@link Sensor} instances.
	 * 
	 * @return the {@link List} of {@link Sensor} instances.
	 */
	public List<Sensor> getSensors() {
		return this.sensors;
	}

	/**
	 * Returns a {@link List} of {@link Actuator} instances.
	 * 
	 * @return the {@link List} of {@link Actuator} instances.
	 */
	public List<Actuator> getActuators() {
		return this.actuators;
	}

	/**
	 * Adds a {@link Sensor} to the {@link List}.
	 * 
	 * @param sensor
	 *            : the {@link Sensor} to be added to the {@link List}.
	 */
	public void addSensor(Sensor sensor) {
		sensor.setBody(this);
		this.sensors.add(sensor);
	}

	/**
	 * Adds an {@link Actuator} to the {@link List}.
	 * 
	 * @param sensor
	 *            : the {@link Actuator} to be added to the {@link List}.
	 */
	public void addActuator(Actuator actuator) {
		actuator.setBody(this);
		this.actuators.add(actuator);
	}

	public Collection<Perception<?>> perceive() {
		// System.out.println("BODY PERCEIVE: " + activatedSensors);
		Set<Perception<?>> perceptions = new HashSet<>();
		activatedSensors
				.forEach((Sensor s) -> {
					s.getPerceptions().forEach(
							(Perception<?> p) -> perceptions.add(p));
				});
		activatedSensors.clear();
		return perceptions;
	}

	public void execute(AbstractEnvironmentalAction action) {
		// System.out.println("BODY EXECUTE: " + action);
		activatedActuators.forEach((Component c) -> c.attempt(action));
		activatedActuators.clear();
	}

	public void sensorActive(Sensor sensor) {
		this.activatedSensors.add(sensor);
	}

	public void actuatorActive(Component component) {
		this.activatedActuators.add(component);
	}

	/**
	 * Getter for the {@link AbstractEnvironment} that this {@link ActiveBody}
	 * resides in.
	 * 
	 * @return the {@link AbstractEnvironment}
	 */
	public AbstractEnvironment getEnvironment() {
		return environment;
	}

	/**
	 * Setter for the {@link AbstractEnvironment} that this {@link ActiveBody}
	 * resides in. Use with caution - only a {@link Physics} should change this!
	 * 
	 * @param environment
	 *            to set
	 */
	public void setEnvironment(AbstractEnvironment environment) {
		this.environment = environment;
	}

	/**
	 * Getter for the default {@link SpeechActuator}.
	 * 
	 * @return the default {@link SpeechActuator}
	 */
	public SpeechActuator getDefaultSpeechActuator() {
		return defaultSpeechActuator;
	}

	/**
	 * Setter for the default {@link SpeechActuator}.
	 * 
	 * @param defaultSpeechActuator
	 */
	public void setDefaultSpeechActuator(SpeechActuator defaultSpeechActuator) {
		this.defaultSpeechActuator = defaultSpeechActuator;
	}

	/**
	 * Getter for the default {@link Sensor}. This may be Look, Smell, Touch,
	 * Listen, or other.
	 * 
	 * @return the default {@link AbstractSensor}
	 */
	public AbstractSensor getDefaultSensor() {
		return defaultSensor;
	}

	/**
	 * Setter for the default {@link Sensor}.
	 * 
	 * @param defaultSensor
	 */
	public void setDefaultSensor(AbstractSensor defaultSensor) {
		this.defaultSensor = defaultSensor;
	}

	public PhysicalActuator getDefaultPhysicalActuator() {
		return defaultPhysicalActuator;
	}

	public void setDefaultPhysicalActuator(
			PhysicalActuator defaultPhysicalActuator) {
		this.defaultPhysicalActuator = defaultPhysicalActuator;
	}

}