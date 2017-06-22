package uk.ac.rhul.cs.dice.starworlds.appearances;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.PhysicalBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Actuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Sensor;

public class ActiveBodyAppearance extends PhysicalBodyAppearance {

	private static final long serialVersionUID = 5676928898552378459L;

	private Collection<Class<? extends Sensor>> sensors;
	private Collection<Class<? extends Actuator>> actuators;

	public ActiveBodyAppearance(String id, Class<? extends PhysicalBody> body,
			Collection<Sensor> sensors, Collection<Actuator> actuators) {
		super(id, body);
		init(sensors, actuators);
	}

	public ActiveBodyAppearance(ActiveBody body) {
		super(body.getClass());
		init(body.getSensors(), body.getActuators());
	}

	private void init(Collection<Sensor> sensors, Collection<Actuator> actuators) {
		this.actuators = new ArrayList<>();
		this.sensors = new ArrayList<>();
		for (Actuator a : actuators) {
			this.actuators.add(a.getClass());
		}
		for (Sensor s : sensors) {
			this.sensors.add(s.getClass());
		}
	}

	public Collection<Class<? extends Sensor>> getSensors() {
		return Collections.unmodifiableCollection(sensors);
	}

	public Collection<Class<? extends Actuator>> getActuators() {
		return Collections.unmodifiableCollection(actuators);
	}

	@Override
	public String represent() {
		return super.represent() + REPSEP + classCollectionToString(sensors)
				+ REPSEP + classCollectionToString(actuators);
	}

	private String classCollectionToString(
			Collection<? extends Class<?>> collection) {
		StringBuilder builder = new StringBuilder("[ ");
		for (Class<?> c : collection) {
			builder.append(c.getSimpleName() + " ");
		}
		builder.append("]");
		return builder.toString();
	}

	@Override
	public String toString() {
		return this.represent();
	}

}
