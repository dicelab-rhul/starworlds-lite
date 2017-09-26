package uk.ac.rhul.cs.dice.starworlds.appearances;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.PhysicalBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.Actuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.Sensor;
import uk.ac.rhul.cs.dice.starworlds.utils.Utils;

public class ActiveBodyAppearance extends PhysicalBodyAppearance {
    private static final long serialVersionUID = 5676928898552378459L;
    private Collection<Class<? extends Sensor>> sensors;
    private Collection<Class<? extends Actuator>> actuators;

    public ActiveBodyAppearance(String id, Class<? extends PhysicalBody> body, Collection<Sensor> sensors, Collection<Actuator> actuators) {
	super(body, id);
	
	init(sensors, actuators);
    }

    public ActiveBodyAppearance(ActiveBody body) {
	super(body.getClass());
	
	init(body.getSensors(), body.getActuators());
    }

    private void init(Collection<Sensor> sensors, Collection<Actuator> actuators) {
	this.actuators = new ArrayList<>();
	this.sensors = new ArrayList<>();
	
	if (actuators != null) {
	    actuators.stream().map(Actuator::getClass).forEach(this.actuators::add);
	}
	
	if (sensors != null) {
	    sensors.stream().map(Sensor::getClass).forEach(this.sensors::add);
	}
    }

    protected void setSensors(Collection<Class<? extends Sensor>> sensors) {
	this.sensors = sensors;
    }

    protected void setActuators(Collection<Class<? extends Actuator>> actuators) {
	this.actuators = actuators;
    }

    public Collection<Class<? extends Sensor>> getSensors() {
	return Collections.unmodifiableCollection(this.sensors);
    }

    public Collection<Class<? extends Actuator>> getActuators() {
	return Collections.unmodifiableCollection(this.actuators);
    }

    @Override
    public String represent() {
	return super.represent() + REPSEP + classCollectionToString(this.sensors) + REPSEP + classCollectionToString(this.actuators);
    }

    private String classCollectionToString(Collection<? extends Class<?>> collection) {
	StringBuilder builder = new StringBuilder("[ ");
	collection.forEach(c -> builder.append(c.getSimpleName() + " "));
	builder.append("]");
	
	return builder.toString();
    }

    @Override
    public String toString() {
	return this.represent();
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + ((this.actuators == null) ? 0 : this.actuators.hashCode());
	result = prime * result + ((this.sensors == null) ? 0 : this.sensors.hashCode());
	
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if(Utils.equalsHelper(this, obj)) {
	    return true;
	}
	
	ActiveBodyAppearance other = (ActiveBodyAppearance) obj;
	
	if(other == null) {
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
}