package uk.ac.rhul.cs.dice.starworlds.environment.physics;

import java.util.Collection;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.CommunicationAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.PhysicalAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SensingAction;
import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.PassiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.starworlds.environment.Environment;
import uk.ac.rhul.cs.dice.starworlds.environment.State;
import uk.ac.rhul.cs.dice.starworlds.perception.AbstractPerception;
import uk.ac.rhul.cs.dice.starworlds.utils.Pair;

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
public interface Physics {

	public void executeActions();

	public Collection<AbstractAgent> getAgents();

	public Collection<ActiveBody> getActiveBodies();

	public Collection<PassiveBody> getPassiveBodies();

	// METHODS TO HANDLE PHYSICAL ACTIONS //

	public void execute(PhysicalAction action, State context);

	public AbstractPerception<?> perform(PhysicalAction action, State context);

	public boolean isPossible(PhysicalAction action, State context);

	public boolean verify(PhysicalAction action, State context);

	// METHODS TO HANDLE COMMUNICATION ACTIONS //

	public void execute(CommunicationAction<?> action, State context);

	public boolean perform(CommunicationAction<?> action, State context);

	public boolean isPossible(CommunicationAction<?> action, State context);

	public boolean verify(CommunicationAction<?> action, State context);

	// METHODS TO HANDLE SENSING ACTIONS //

	public void execute(SensingAction action, State context);

	public Set<Pair<String, Object>> perform(SensingAction action, State context);

	public boolean isPossible(SensingAction action, State context);

	public boolean verify(SensingAction action, State context);

	public void setEnvironment(Environment environment);

	public void notify(Class<?> sensorclass, ActiveBody activeBody,
			AbstractPerception<?> perception);

	/*
	 * attempt execute - ispossible, perform, verify, notify
	 */

}