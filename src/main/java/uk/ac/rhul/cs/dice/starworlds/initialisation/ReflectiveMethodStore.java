package uk.ac.rhul.cs.dice.starworlds.initialisation;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.PhysicalAction;
import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.Sensor;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.Ambient;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.AbstractEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.Environment;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.Physics;
import uk.ac.rhul.cs.dice.starworlds.perception.AbstractPerception;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;

/**
 * A static class that performs validation of methods in an application. There
 * are a number of methods that must be defined in an applications
 * {@link Physics} for any {@link PhysicalAction}s that have been defined. This
 * class finds and stores all such methods for reference later by the class that
 * should be calling them. Two such classes are {@link PhysicalAction} and
 * {@link AbstractEnvironment}.
 * 
 * @author Ben Wilkins
 *
 */
public final class ReflectiveMethodStore {

	// ****************************************** //
	// ******* AbstractEnvironment Methods ****** //
	// ****************************************** //

	/**
	 * Name of the method that should be called when checking if a
	 * {@link Perception} is perceivable by a given {@link Sensor}.
	 */
	public static final String PERCEIVABLE = "perceivable";
	// parameters of the AbstractEnvironment method above
	private static final Class<?>[] PERCEIVABLEPARAMS = new Class<?>[] { null,
			AbstractPerception.class, Ambient.class };

	// ****************************************** //
	// ********* PhysicalAction Methods ********* //
	// ****************************************** //
/**
	 * Name of the method that should be called when getting {@link Perception}s
	 * for an {@link ActiveBody} that performed a given {@link PhysicalAction}. </br> This
	 * method is called by default in the
	 * {@link PhysicalAction#getAgentPerceptions(Physics, Ambient)} method. </br>
	 * This method must be defined in the {@link Physics} of the application
	 * that will use the {@link PhysicalAction}. It should be defined with parameters:
	 * (Action, State) with Action being the class of the given {@link PhysicalAction}.
	 * </br> The method must have the return type: {@link Collection<
	 * 
	 * @link AbstractPerception}<?>>}.
	 */
	public static final String GETAGENTPERCEPTIONS = "getAgentPerceptions";
	private static Method COMPAREGETAGENTPERCEPTIONS;

/**
	 * Name of the method that should be called when getting {@link Perception}s
	 * for an all other {@link ActiveBody}s when an given {@link ActiveBody} has
	 * performed a given {@link PhysicalAction}. </br> This method is called by default in the
	 * {@link PhysicalAction#getOtherPerceptions(Physics, Ambient)} method. </br>
	 * This method must be defined in the {@link Physics} of the application
	 * that will use the {@link PhysicalAction}. It should be defined with parameters:
	 * (Action, State) with Action being the class of the given {@link PhysicalAction}.
	 * </br> The method must have the return type: {@link Collection<
	 * 
	 * @link AbstractPerception}<?>>}.
	 */
	public static final String GETOTHERPERCEPTIONS = "getOtherPerceptions";
	private static Method COMPAREGETOTHERPERCEPTIONS;

	/**
	 * The name of the method that should be called when an {@link Action}
	 * isPossible that will perform the {@link Action}. </br> This method is
	 * called by default in the {@link PhysicalAction#perform(Physics, Ambient)}
	 * method. </br> This method must be defined in the {@link Physics} of the
	 * application that will use the {@link PhysicalAction}. It should be
	 * defined with parameters: (Action, State) with Action being the class of
	 * the given {@link PhysicalAction}. </br> The method must have the return
	 * type: {@link Boolean}.
	 */
	public static final String PERFORM = "perform";
	private static Method COMPAREPERFORM;
	/**
	 * The name of the method that should be called to check if a given
	 * {@link PhysicalAction} is possible give the current {@link Physics} and
	 * {@link Ambient} of the {@link Environment}. </br> This method is called
	 * by default in the {@link PhysicalAction#isPossible(Physics, Ambient)}
	 * method. </br> This method must be defined in the {@link Physics} of the
	 * application that will use the {@link PhysicalAction}. </br> The method
	 * must have the return type: {@link Boolean}.
	 */
	public static final String ISPOSSIBLE = "isPossible";
	private static Method COMPAREISPOSSIBLE;

	/**
	 * The name of the method that should be called to check that the given
	 * {@link PhysicalAction} was successfully performed. </br> This method is
	 * called by default in the {@link PhysicalAction#verify(Physics, Ambient)}
	 * method. </br> This method must be defined in the {@link Physics} of the
	 * application that will use the {@link PhysicalAction}. </br> The method
	 * must have the return type: {@link Boolean}.
	 */
	public static final String VERIFY = "verify";
	private static Method COMPAREVERIFY;
	// parameters of the PhysicalAction methods above
	private static final Class<?>[] ACTIONPARAMS = new Class<?>[] {
			PhysicalAction.class, Ambient.class };

	static {
		try {
			ACTIONPARAMS[0] = PhysicalAction.class;
			COMPAREGETAGENTPERCEPTIONS = Physics.class.getMethod(
					GETAGENTPERCEPTIONS, ACTIONPARAMS);
			COMPAREGETOTHERPERCEPTIONS = Physics.class.getMethod(
					GETOTHERPERCEPTIONS, ACTIONPARAMS);
			COMPAREPERFORM = Physics.class.getMethod(PERFORM, ACTIONPARAMS);
			COMPAREISPOSSIBLE = Physics.class.getMethod(ISPOSSIBLE,
					ACTIONPARAMS);
			COMPAREVERIFY = Physics.class.getMethod(VERIFY, ACTIONPARAMS);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}

	private static Map<Class<? extends Action>, Map<String, Method>> actionmethods = new HashMap<>();

	public static Method getActionMethod(Action action, String methodname) {
		return actionmethods.get(action.getClass()).get(methodname);
	}

	/**
	 * This method should be called during the parse of the system configuration
	 * to ensure that the required methods are present in an application.
	 * 
	 * @param physics
	 * @param actions
	 * @param sensors
	 */
	public static void validateReflectiveSensors(
			Class<? extends Physics> physics,
			Collection<Class<? extends Sensor>> sensors) {
		for (Class<? extends Sensor> c : sensors) {
			PERCEIVABLEPARAMS[0] = c;
			Method m = getMethod(physics, PERCEIVABLE, PERCEIVABLEPARAMS);
			if (m != null) {
				if (m.getReturnType().isPrimitive()) {
					if (m.getReturnType().equals(boolean.class)) {
						return;
					}
				}
			}
			throw new IllegalConfigurationException(PERCEIVABLE, boolean.class,
					PERCEIVABLEPARAMS, physics);
		}
	}

	/**
	 * This method should be called during the parse of the system configuration
	 * to ensure that the required methods are present in an application.
	 * 
	 * @param physics
	 * @param actions
	 */
	public static void validateReflectiveActions(
			Class<? extends Physics> physics,
			Collection<Class<? extends AbstractEnvironmentalAction>> actions) {
		for (Class<? extends Action> a : actions) {
			if (PhysicalAction.class.isAssignableFrom(a)) {
				ACTIONPARAMS[0] = a;
				Map<String, Method> methodmap = new HashMap<>();
				actionmethods.put(a, methodmap);
				doActionMethod(physics, GETAGENTPERCEPTIONS, ACTIONPARAMS,
						COMPAREGETAGENTPERCEPTIONS, methodmap);
				doActionMethod(physics, GETOTHERPERCEPTIONS, ACTIONPARAMS,
						COMPAREGETOTHERPERCEPTIONS, methodmap);
				doActionMethod(physics, PERFORM, ACTIONPARAMS, COMPAREPERFORM,
						methodmap);
				doActionMethod(physics, ISPOSSIBLE, ACTIONPARAMS,
						COMPAREISPOSSIBLE, methodmap);
				doActionMethod(physics, VERIFY, ACTIONPARAMS, COMPAREVERIFY,
						methodmap);
				//System.out.println("     " + methodmap);
			}
		}
	}

	private static void doActionMethod(Class<?> physics, String name,
			Class<?>[] params, Method compare, Map<String, Method> methodmap) {
		Method m = getMethod(physics, name, params);
		if (m != null) {
			if (compareReturnTypes(m, compare)) {
				methodmap.put(name, m);
				return;
			}
		}
		throw new IllegalConfigurationException(name,
				compare.getGenericReturnType(), params, physics);
	}

	private static boolean compareReturnTypes(Method submethod,
			Method supermethod) {
		Class<?> r1 = submethod.getReturnType();
		Class<?> r2 = supermethod.getReturnType();
		if (r1.equals(Void.TYPE)) {
			return r2.equals(Void.TYPE);
		}
		if (r1.isPrimitive()) {
			return r1.equals(r2);
		}
		if (r1.getTypeParameters().length == 0) {
			return r2.isAssignableFrom(r1);
		} else {
			String[] ssub = submethod.getGenericReturnType().getTypeName()
					.split("<", 2);
			String[] ssup = supermethod.getGenericReturnType().getTypeName()
					.split("<", 2);
			return getClassFromString(ssup[0]).isAssignableFrom(
					getClassFromString(ssub[0]))
					&& ssup[1].equals(ssub[1]);
		}
	}

	public static Class<?> getClassFromString(String c) {
		try {
			return Class.forName(c);
		} catch (ClassNotFoundException e) {
			System.err.println("Could not find class: " + e.getMessage());
			System.out.println("VALIDATION FAILED");
			System.exit(-1);
			return null;
		}
	}

	public static Method getMethod(Class<?> getFrom, String name,
			Class<?>[] params) {
		try {
			return getFrom.getMethod(name, params);
		} catch (NoSuchMethodException | SecurityException e) {
			System.err.println("Error retriving method: "
					+ methodToString(getFrom, name, params));
			return null;
		}
	}

	public static String methodToString(Class<?> getFrom, String name,
			Class<?>[] params) {
		StringBuilder b = new StringBuilder(getFrom.getSimpleName() + " : "
				+ name + "(");
		if (params.length > 0) {
			for (int i = 0; i < params.length - 1; i++) {
				b.append(params[i] + ",");
			}
			b.append(params[params.length - 1] + ")");
		} else {
			b.append(")");
		}
		return b.toString();
	}

	public static String methodToString(Type returntype, String name,
			Class<?>[] params) {
		StringBuilder b = new StringBuilder(returntype + " " + name + "(");
		for (int i = 0; i < params.length - 1; i++) {
			b.append(params[i] + ",");
		}
		b.append(params[params.length - 1] + ")");
		return b.toString();
	}

	public static class IllegalConfigurationException extends RuntimeException {

		private static final long serialVersionUID = -340309113871139773L;

		public IllegalConfigurationException(String method, Type returntype,
				Class<?>[] params, Class<?> missingFrom) {
			super(System.lineSeparator() + "The method: "
					+ System.lineSeparator()
					+ methodToString(returntype, method, params)
					+ System.lineSeparator()
					+ "is required and is missing from: " + missingFrom);
		}

		public IllegalConfigurationException(String message) {
			super(message);
		}
	}
}
