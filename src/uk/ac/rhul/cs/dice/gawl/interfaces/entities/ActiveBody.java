package uk.ac.rhul.cs.dice.gawl.interfaces.entities;

import java.util.ArrayList;
import java.util.List;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.environmental.EnvironmentalAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.appearances.AbstractAppearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.components.Actuator;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.components.Sensor;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.components.concrete.ListeningSensor;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.components.concrete.SeeingSensor;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.AbstractEnvironment;

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

	private SeeingSensor defaultSeeingSensor;
	private ListeningSensor defaultListeningSensor;
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
		for (Actuator a : actuators) {
			this.addActuator(a);
		}

		for (Sensor s : sensors) {
			this.addSensor(s);
		}

		this.setDefaultSeeingSensor((SeeingSensor) this.sensors
				.stream()
				.filter((Sensor s) -> SeeingSensor.class.isAssignableFrom(s
						.getClass())).findFirst().get());
		this.setDefaultListeningSensor((ListeningSensor) this.sensors
				.stream()
				.filter((Sensor s) -> ListeningSensor.class.isAssignableFrom(s
						.getClass())).findFirst().get());
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

	public AbstractEnvironment getEnvironment() {
		return environment;
	}

	public void setEnvironment(AbstractEnvironment environment) {
		this.environment = environment;
	}

	public ListeningSensor getDefaultListeningSensor() {
		return this.defaultListeningSensor;
	}

	public SeeingSensor getDefaultSeeingSensor() {
		return this.defaultSeeingSensor;
	}

	public void setDefaultSeeingSensor(SeeingSensor sensor) {
		this.defaultSeeingSensor = sensor;
	}

	public void setDefaultListeningSensor(ListeningSensor sensor) {
		this.defaultListeningSensor = sensor;
	}
}