package uk.ac.rhul.cs.dice.starworlds.environment.physics;

import java.util.Collection;
import java.util.Map;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.CommunicationAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.PhysicalAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SensingAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.ActiveBodyAppearance;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.AbstractSensor;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.Sensor;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.concrete.ListeningSensor;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.concrete.SeeingSensor;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.Ambient;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.AbstractEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.Environment;
import uk.ac.rhul.cs.dice.starworlds.perception.AbstractPerception;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;
import uk.ac.rhul.cs.dice.starworlds.utils.Identifiable;

/**
 * The general interface for all the physics.<br/>
 * <br/>
 * 
 * Known implementations: {@link AbstractPhysics}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public interface Physics extends Identifiable {

	/**
	 * This method should return the {@link Perception} that all agents will
	 * receive regardless of their {@link Action}s. This {@link Perception}
	 * defaults to null.
	 * 
	 * @param body
	 *            : of the perceiving
	 * @param action
	 *            : that the body attempted this cycle
	 * @param context
	 *            : of the {@link Environment}
	 * @returns the perception
	 */
	public default Collection<AbstractPerception<?>> activeBodyPerceive(
			ActiveBodyAppearance body, Action action, Ambient context) {
		return null;
	}

	public void runActors();

	public void executeActions();

	// METHODS TO HANDLE PHYSICAL ACTIONS //

	public boolean execute(PhysicalAction action, Ambient context);

	public boolean perform(PhysicalAction action, Ambient context)
			throws Exception;

	public boolean isPossible(PhysicalAction action, Ambient context)
			throws Exception;

	public boolean verify(PhysicalAction action, Ambient context)
			throws Exception;

	public default Collection<AbstractPerception<?>> getAgentPerceptions(
			PhysicalAction action, Ambient context) {
		return null;
	}

	public default Collection<AbstractPerception<?>> getOtherPerceptions(
			PhysicalAction action, Ambient context) {
		return null;
	}

	// METHODS TO HANDLE COMMUNICATION ACTIONS //

	public void execute(CommunicationAction<?> action, Ambient context);

	public boolean perform(CommunicationAction<?> action, Ambient context);

	public boolean isPossible(CommunicationAction<?> action, Ambient context);

	public boolean verify(CommunicationAction<?> action, Ambient context);

	// METHODS TO HANDLE SENSING ACTIONS //

	public void execute(SensingAction action, Ambient context);

	public Map<String, Object> perform(SensingAction action, Ambient context);

	public boolean isPossible(SensingAction action, Ambient context);

	public boolean verify(SensingAction action, Ambient context);

	public void setEnvironment(AbstractEnvironment environment);

	/**
	 * The perceivable method for all sensors. This method will never be called.
	 * Perceivable methods should be defined for all subclasses of
	 * {@link AbstractSensor}. These methods define that any given
	 * {@link Sensor} can perceive. They should check whether the given
	 * {@link Perception} can be perceived by the {@link Sensor} given the
	 * current {@link Ambient} of the {@link Environment}. (although in some
	 * cases the ability to perceive may not depend on the {@link Ambient}). All
	 * perceivable methods must be define as here but replacing the
	 * {@link AbstractSensor} class with the given subclass. A simple example of
	 * this is given
	 * {@link AbstractPhysics#perceivable(ListeningSensor, AbstractPerception, Ambient)}
	 * and
	 * {@link AbstractPhysics#perceivable(SeeingSensor, AbstractPerception, Ambient)}
	 * 
	 * @param sensor
	 *            that will be perceiving the given {@link Perception}
	 * @param perception
	 *            the {@link Perception} that may or may not be perceived by the
	 *            sensor
	 * @param context
	 *            {@link Ambient} of the {@link Environment} currently
	 * @return true if the given {@link Sensor} can perceive the given
	 *         {@link Perception}, false otherwise.
	 */
	public boolean perceivable(AbstractSensor sensor,
			AbstractPerception<?> perception, Ambient context);

}