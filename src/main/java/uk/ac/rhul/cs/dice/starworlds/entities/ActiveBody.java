package uk.ac.rhul.cs.dice.starworlds.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.EnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.ActiveBodyAppearance;
import uk.ac.rhul.cs.dice.starworlds.appearances.Appearance;
import uk.ac.rhul.cs.dice.starworlds.appearances.PhysicalBodyAppearance;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractAutonomousAgent;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.AbstractSensor;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.Actuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.Component;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.Sensor;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.concrete.PhysicalActuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.concrete.SpeechActuator;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.AbstractEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.Physics;
import uk.ac.rhul.cs.dice.starworlds.utils.Utils;

/**
 * A subclass of {@link PhysicalBody} capable to perform an
 * {@link EnvironmentalAction}, thus implementing {@link Actor} It has a
 * {@link List} of {@link Sensor} elements and one of {@link Actuator}
 * elements.<br/>
 * <br/>
 * 
 * Known direct subclasses: {@link AbstractAutonomousAgent},
 * {@link DependentBody}.
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
    private Set<Sensor> activatedSensors;
    private Set<Component> activatedActuators;
    private List<Sensor> sensors;
    private List<Actuator> actuators;
    private AbstractEnvironment environment;

    /**
     * Constructor. The default {@link Appearance} for an {@link ActiveBody} is
     * created automatically.
     * 
     * @param sensors
     *            : a {@link List} of {@link Sensor} instances.
     * @param actuators
     *            : a {@link List} of {@link Actuator} instances.
     */
    public ActiveBody(List<Sensor> sensors, List<Actuator> actuators) {
	super(null); //TODO are we sure it is not just super() ?
	init(sensors, actuators);
	
	this.setAppearance(new ActiveBodyAppearance(this));
    }

    /**
     * Constructor.
     * 
     * @param appearance
     *            : the {@link Appearance} of the {@link ActiveBody}.
     * @param sensors
     *            : a {@link List} of {@link Sensor} instances.
     * @param actuators
     *            : a {@link List} of {@link Actuator} instances.
     */
    public ActiveBody(ActiveBodyAppearance appearance, List<Sensor> sensors, List<Actuator> actuators) {
	super(appearance);
	
	init(sensors, actuators);
    }

    private void init(List<Sensor> sensors, List<Actuator> actuators) {
	this.sensors = new ArrayList<>();
	this.actuators = new ArrayList<>();
	this.activatedActuators = new HashSet<>();
	this.activatedSensors = new HashSet<>();

	if(actuators != null) {
	    actuators.forEach(this::addActuator);
	}
	
	if(sensors != null) {
	    sensors.forEach(this::addSensor);
	}

	this.setDefaultSensor((AbstractSensor) findSensorByClass(AbstractSensor.class));
	this.setDefaultSpeechActuator((SpeechActuator) findActuatorByClass(SpeechActuator.class));
	this.setDefaultPhysicalActuator((PhysicalActuator) findActuatorByClass(PhysicalActuator.class));
    }

    public Sensor findSensorByClass(Class<?> c) {
	return (Sensor) findComponentByClass(c, this.sensors);
    }

    public Actuator findActuatorByClass(Class<?> c) {
	return (Actuator) findComponentByClass(c, this.actuators);
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

    public Collection<Object> perceive() {
	Set<Object> perceptions = new HashSet<>();
	this.activatedSensors.forEach(s -> s.getPerceptions().forEach(perceptions::add));	
	this.activatedSensors.clear();
	
	return perceptions;
    }

    public void execute(AbstractEnvironmentalAction action) {
	this.activatedActuators.forEach(c -> c.attempt(action));
	this.activatedActuators.clear();
    }

    @Override
    public void setId(String id) {
	super.setId(id);
	
	for (int i = 0; i < this.sensors.size(); i++) {
	    this.sensors.get(i).setId(this.getId() + ":" + i);
	}
	
	for (int i = 0; i < this.actuators.size(); i++) {
	    this.actuators.get(i).setId(this.getId() + ":" + i);
	}
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

    public void setDefaultPhysicalActuator(PhysicalActuator defaultPhysicalActuator) {
	this.defaultPhysicalActuator = defaultPhysicalActuator;
    }

    @Override
    public ActiveBodyAppearance getAppearance() {
	return (ActiveBodyAppearance) super.getAppearance();
    }

    @Override
    public void setAppearance(PhysicalBodyAppearance appearance) {
	super.setAppearance((ActiveBodyAppearance) appearance);
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + ((this.activatedActuators == null) ? 0 : this.activatedActuators.hashCode());
	result = prime * result + ((this.activatedSensors == null) ? 0 : this.activatedSensors.hashCode());
	result = prime * result + ((this.actuators == null) ? 0 : this.actuators.hashCode());
	result = prime * result + ((this.defaultPhysicalActuator == null) ? 0 : this.defaultPhysicalActuator.hashCode());
	result = prime * result + ((this.defaultSensor == null) ? 0 : this.defaultSensor.hashCode());
	result = prime * result + ((this.defaultSpeechActuator == null) ? 0 : this.defaultSpeechActuator.hashCode());
	result = prime * result + ((this.environment == null) ? 0 : this.environment.hashCode());
	result = prime * result + ((this.sensors == null) ? 0 : this.sensors.hashCode());
	
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if(Utils.equalsHelper(this, obj)) {
	    return true;
	}
	
	ActiveBody other = (ActiveBody) obj;
	
	if(other == null) {
	    return false;
	}
	
	return checkAttributes(other);
    }

    private boolean checkAttributes(ActiveBody other) {
	return checkSensors(other) && checkActuators(other);
    }

    private boolean checkSensors(ActiveBody other) {
	if (this.activatedSensors == null) {
	    if (other.activatedSensors != null) {
		return false;
	    }
	}
	else if (!this.activatedSensors.equals(other.activatedSensors)) {
	    return false;
	}
	
	if (this.defaultSensor == null) {
	    if (other.defaultSensor != null) {
		return false;
	    }
	}
	else if (!this.defaultSensor.equals(other.defaultSensor)) {
	    return false;
	}   
	
	if (this.sensors == null) {
	    if (other.sensors != null) {
		return false;
	    }
	}
	else if (!this.sensors.equals(other.sensors)) {
	    return false;
	}
	
	return true;
    }

    private boolean checkActuators(ActiveBody other) {
	if (this.activatedActuators == null) {
	    if (other.activatedActuators != null) {
		return false;
	    }
	}
	else if (!this.activatedActuators.equals(other.activatedActuators)) {
	    return false;
	}
	
	if (this.actuators == null) {
	    if (other.actuators != null) {
		return false;
	    }	
	}
	else if (!this.actuators.equals(other.actuators)) {
	    return false;
	}	    
	
	return checkDefaultActuators(other);
    }

    private boolean checkDefaultActuators(ActiveBody other) {
	if (this.defaultPhysicalActuator == null) {
	    if (other.defaultPhysicalActuator != null) {
		return false;
	    }		
	}
	else if (!this.defaultPhysicalActuator.equals(other.defaultPhysicalActuator)) {
	    return false;
	}	    
	
	if (this.defaultSpeechActuator == null) {
	    if (other.defaultSpeechActuator != null) {
		return false;
	    }		
	}
	else if (!this.defaultSpeechActuator.equals(other.defaultSpeechActuator)) {
	    return false;
	}  
	
	return true;
    }
}