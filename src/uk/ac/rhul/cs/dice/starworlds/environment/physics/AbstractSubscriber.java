package uk.ac.rhul.cs.dice.starworlds.environment.physics;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.AbstractSensor;
import uk.ac.rhul.cs.dice.starworlds.perception.AbstractPerception;

public abstract class AbstractSubscriber {

	@Retention(RetentionPolicy.RUNTIME)
	public @interface SensiblePerception {
	}

	// map: agentid -> map: sensorclass -> sensor
	protected Map<String, Map<Class<? extends AbstractSensor>, AbstractSensor>> subscribedSensors;
	protected Map<Class<? extends AbstractPerception>, Set<Class<? extends AbstractSensor>>> perceptionSensors;
	protected Map<Class<? extends AbstractEnvironmentalAction>, Set<Class<? extends AbstractPerception>>> actionPerceptions;
	protected Set<Class<? extends AbstractSensor>> sensors;
	protected Set<Class<? extends AbstractEnvironmentalAction>> actions;

	public AbstractSubscriber() {
		perceptionSensors = new HashMap<>();
		subscribedSensors = new HashMap<>();
		actionPerceptions = new HashMap<>();
		sensors = new HashSet<>();
		actions = new HashSet<>();
	}

	public void setPossibleActions(
			Collection<Class<? extends AbstractEnvironmentalAction>> actions) {
		if (actionPerceptions.isEmpty()) {
			actions.forEach((Class<? extends AbstractEnvironmentalAction> c) -> {
				addPossibleAction(c);
			});
		}
	}

	public void addPossibleAction(
			Class<? extends AbstractEnvironmentalAction> action) {
		while (AbstractEnvironmentalAction.class.isAssignableFrom(action
				.getSuperclass())) {
			if (!actions.contains(action)) {
				Collection<Class<?>> classes = findClassTypeFieldsWithAnnotation(
						action, SensiblePerception.class);
				for (Class<?> c : classes) {
					if (AbstractPerception.class.isAssignableFrom(c)) {
						Class<? extends AbstractPerception> perception = c
								.asSubclass(AbstractPerception.class);
						actions.add(action);
						actionPerceptions.putIfAbsent(action, new HashSet<>());
						actionPerceptions.get(action).add(perception);
					}
				}
			}
			action = action.getSuperclass().asSubclass(
					AbstractEnvironmentalAction.class);
		}
	}

	public Map<Class<? extends AbstractPerception>, Set<AbstractSensor>> findSensors(
			ActiveBody body, AbstractEnvironmentalAction action) {
		Map<Class<? extends AbstractSensor>, AbstractSensor> sensormap = subscribedSensors
				.get(body.getId());
		Map<Class<? extends AbstractPerception>, Set<AbstractSensor>> sensors = new HashMap<>();
		actionPerceptions.get(action.getClass()).forEach(
				(Class<? extends AbstractPerception> p) -> {
					Set<AbstractSensor> sas;
					sensors.put(p, (sas = new HashSet<>()));
					perceptionSensors.get(p).forEach(
							(Class<? extends AbstractSensor> c) -> {
								sas.add(sensormap.get(c));
							});
				});
		return sensors;
	}

	public void addNewSensorType(AbstractSensor sensor) {
		if (sensor != null) {
			if (AbstractSensor.class.isAssignableFrom(sensor.getClass())) {
				addNewSensorType(sensor.getClass());
			} else {
				System.out.println("TODO");
				Thread.dumpStack();
			}

		}
	}

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

	public void subscribe(ActiveBody body, AbstractSensor[] sensors) {
		Map<Class<? extends AbstractSensor>, AbstractSensor> sensormap = subscribedSensors
				.putIfAbsent(body.getId(), new HashMap<>());
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

	public Map<String, Map<Class<? extends AbstractSensor>, AbstractSensor>> getSubscribedSensors() {
		return subscribedSensors;
	}

	public Set<Class<? extends AbstractSensor>> getSensors() {
		return sensors;
	}

	protected <A> Collection<Class<?>> findClassTypeFieldsWithAnnotation(
			Class<? extends A> type, Class<? extends Annotation> annotation) {
		Collection<Class<?>> result = new HashSet<>();
		if (type != null) {
			Field[] fields = type.getDeclaredFields();
			for (Field f : fields) {
				if (Modifier.isStatic(f.getModifiers())) {
					if (f.isAnnotationPresent(annotation)) {
						if (Class.class.isAssignableFrom(f.getType())) {
							try {
								result.add((Class<?>) f.get(null));
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
		return result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PERCEPTIONS -> SENSORS" + System.lineSeparator());
		perceptionSensors.forEach((Class<? extends AbstractPerception> action,
				Set<Class<? extends AbstractSensor>> sensors) -> {
			builder.append(action.getSimpleName() + ": { ");
			sensors.forEach((Class<? extends AbstractSensor> sensor) -> builder
					.append(sensor.getSimpleName() + " "));
			builder.append("}" + System.lineSeparator());
		});
		builder.append("ACTIONS -> PERCEPTIONS" + System.lineSeparator());
		actionPerceptions
				.forEach((Class<? extends Action> action,
						Set<Class<? extends AbstractPerception>> perceptions) -> {
					builder.append(action.getSimpleName() + ": { ");
					perceptions
							.forEach((
									Class<? extends AbstractPerception> perception) -> builder
									.append(perception.getSimpleName() + " "));
					builder.append("}" + System.lineSeparator());
				});
		return builder.toString();
	}
}
