package uk.ac.rhul.cs.dice.starworlds.environment.physics;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.AbstractSensor;
import uk.ac.rhul.cs.dice.starworlds.perception.AbstractPerception;

public class Subscriber extends AbstractSubscriber<AbstractSensor> {


	@Override
	public void addNewSensorType(Class<? extends AbstractSensor> sensor) {
		Class<? extends AbstractSensor> realsensor = sensor;
		if (!sensors.contains(sensor)) {
			while (AbstractSensor.class
					.isAssignableFrom(sensor.getSuperclass())) {

				Collection<Class<?>> classes = findClassTypeFieldsWithAnnotation(
						sensor, SensiblePerception.class);
				for (Class<?> c : classes) {
					if (AbstractPerception.class.isAssignableFrom(c)) {
						Class<? extends AbstractPerception> perception = c
								.asSubclass(AbstractPerception.class);
						perceptionSensors.putIfAbsent(perception,
								new HashSet<>());
						perceptionSensors.get(perception).add(realsensor);
						sensors.add(realsensor);
					}
				}
				sensor = sensor.getSuperclass()
						.asSubclass(AbstractSensor.class);
			}
		}
	}

	@Override
	public void subscribe(ActiveBody body, AbstractSensor[] sensors) {
		Map<Class<? extends AbstractSensor>, AbstractSensor> sensormap = subscribedSensors.putIfAbsent(
				body.getId(), new HashMap<>());
		System.out.println("SUBSCRIBING: " + Arrays.toString(sensors));
		if (sensormap == null) {
			sensormap = subscribedSensors.get(body.getId());
		}
		for (AbstractSensor s : sensors) {
			AbstractSensor as = (AbstractSensor) s;
			if (!this.sensors.contains(as.getClass())) {
				this.addNewSensorType(as.getClass());
			}
			if (sensormap.putIfAbsent(as.getClass(), as) != null) {
				System.err.println("WARNING: Sensor class: "
						+ s.getClass().getSimpleName()
						+ " is over-subscribed for agent: " + body
						+ System.lineSeparator() + " Sensor: " + as
						+ " was not subscribed");
			}
		}
	}
}
