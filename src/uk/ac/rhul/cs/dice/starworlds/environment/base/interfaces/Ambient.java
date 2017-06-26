package uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.CommunicationAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.PhysicalAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SensingAction;
import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.PassiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.starworlds.environment.base.AbstractAmbient;
import uk.ac.rhul.cs.dice.starworlds.environment.base.AbstractAmbient.Filter;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.Physics;
import uk.ac.rhul.cs.dice.starworlds.utils.Pair;

/**
 * The interface for ambients.<br/>
 * <br/>
 * 
 * Known implementations: {@link AbstractAmbient}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public interface Ambient {

	/**
	 * This method should be called when some {@link SensingAction} is trying to
	 * ask for environment variables. A {@link Physics} should first validate
	 * the keys (i.e check for the agents permissions). Note that if an invalid
	 * key is provided null will be given as the associated environment
	 * variable. One may wish to create protected views of environment variables
	 * returned, if an agent should not be able to modify them for example.
	 * 
	 * @param keys
	 *            an array of keys to environmental variables.
	 * @param action
	 *            the {@link SensingAction} that began the call
	 * @return a {@link Set} of {@link Pair}s consisting of < {@link String} ,
	 *         {@link Object} > where the {@link String} is the key and the
	 *         {@link Object} is its associated environment variable. The
	 *         returned {@link Object} will be {@link Serializable} if it is to
	 *         be sent overnetwork.
	 */
	public Set<Pair<String, Object>> filterActivePerception(String[] keys,
			SensingAction action);

	public Collection<AbstractAgent> getAgents();

	public Collection<ActiveBody> getActiveBodies();

	public Collection<PassiveBody> getPassiveBodies();

	public boolean addEnvironmentVariable(String key, Object variable);

	public boolean filterExists(String key);

	public boolean environmentVariableExists(String key);

	public String getEnvironmentVariableKeysAsString();

	public Set<String> getEnvironmentVariableKeys();

	public Set<String> getFilterKeys();

	public String getFilterKeysAsString();

	public boolean addFilter(String key, Filter filter);

	public void filterAction(AbstractEnvironmentalAction action);

	public void addSenseAction(SensingAction action);

	public void addPhysicalAction(PhysicalAction action);

	public void addCommunicationAction(CommunicationAction<?> action);

	public Collection<SensingAction> flushSensingActions();

	public Collection<PhysicalAction> flushPhysicalActions();

	public Collection<CommunicationAction<?>> flushCommunicationActions();

	public Collection<SensingAction> getSensingActions();

	public Collection<PhysicalAction> getPhysicalActions();

	public Collection<CommunicationAction<?>> getCommunicationActions();

}