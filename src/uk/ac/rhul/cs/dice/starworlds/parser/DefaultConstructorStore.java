package uk.ac.rhul.cs.dice.starworlds.parser;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import uk.ac.rhul.cs.dice.starworlds.appearances.AbstractAppearance;
import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.PassiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractAgent;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractAgentMind;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.AbstractActuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.AbstractSensor;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.AbstractAmbient;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractPhysics;

public class DefaultConstructorStore {

	/**
	 * Annotation that indicates which {@link Constructor} should be used as the
	 * default for a default instance class. If no {@link Parser#CONSTRUCT}
	 * property is given to an instance in the configuration file, the default
	 * {@link Constructor} will be used.
	 * 
	 * @author Ben
	 *
	 */
	@Retention(RetentionPolicy.RUNTIME)
	public @interface DefaultConstructor {
	}

	private static final DefaultConstructorStore INSTANCE = new DefaultConstructorStore();

	private static final Map<String, Constructor<?>> CONSTRUCTORS = new HashMap<>();
	private static final Map<String, Class<?>> DEFAULTCLASSES = new HashMap<>();

	static {
		// store default classes
		DEFAULTCLASSES.put(Parser.AGENTS, AbstractAgent.class);
		DEFAULTCLASSES.put(Parser.AGENTMINDS, AbstractAgentMind.class);
		DEFAULTCLASSES.put(Parser.AGENTACTUATORS, AbstractActuator.class);
		DEFAULTCLASSES.put(Parser.AGENTSENSORS, AbstractSensor.class);
		DEFAULTCLASSES.put(Parser.PHYSICS, AbstractPhysics.class);
		DEFAULTCLASSES.put(Parser.STATES, AbstractAmbient.class);
		DEFAULTCLASSES.put(Parser.ACTIVEBODIES, ActiveBody.class);
		DEFAULTCLASSES.put(Parser.PASSIVEBODIES, PassiveBody.class);
		DEFAULTCLASSES.put(Parser.APPEARANCES, AbstractAppearance.class);
		// get all default constructors
		DEFAULTCLASSES.forEach((k, c) -> {
			CONSTRUCTORS.put(k, getDefaultConstructor(c));
		});
		System.out.println("RUNNING WITH DEFAULT CLASSES:");
		DEFAULTCLASSES.forEach((k, c) -> System.out
				.println("  " + k + "->" + c));
		System.out.println("RUNNING WITH DEFAULT CONSTRUCTORS:");
		CONSTRUCTORS.forEach((k, c) -> System.out.println("  " + k + "->" + c));
	}

	private static Constructor<?> getDefaultConstructor(Class<?> c) {
		Constructor<?>[] constructors = c.getConstructors();
		for (Constructor<?> constructor : constructors) {
			if (constructor.getAnnotation(DefaultConstructor.class) != null) {
				return constructor;
			}
		}
		System.out.println("ERROR, NO DEFAULT CONSTRUCTOR DEFINED FOR CLASS: "
				+ c + System.lineSeparator() + " EXITING...");
		System.exit(-1);
		return null;
	}

	public static Constructor<?> getConstructor(String key) {
		return CONSTRUCTORS.get(key);
	}

	public static DefaultConstructorStore getInstance() {
		return INSTANCE;
	}
}
