package uk.ac.rhul.cs.dice.starworlds.environment.ambient;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.CommunicationAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.PhysicalAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SensingAction;
import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.Agent;
import uk.ac.rhul.cs.dice.starworlds.entities.PassiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractAutonomousAgent;
import uk.ac.rhul.cs.dice.starworlds.entities.avatar.AbstractAvatarAgent;
import uk.ac.rhul.cs.dice.starworlds.entities.avatar.Avatar;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.filter.Filter;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.AbstractEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.Environment;
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
	 *            the {@link AbstractEnvironmentalAction} that began the call
	 * @return a {@link Set} of {@link Pair}s consisting of < {@link String} ,
	 *         {@link Object} > where the {@link String} is the key and the
	 *         {@link Object} is its associated environment variable. The
	 *         returned {@link Object} will be {@link Serializable} if it is to
	 *         be sent overnetwork.
	 */
	public Set<Pair<String, Object>> filterActivePerception(String[] keys,
			AbstractEnvironmentalAction action);

	/**
	 * Adds the given {@link Agent} to this {@link Ambient}. This method should
	 * only be called outside of out the {@link Environment} cycle to prevent
	 * conflict of access. This method does not subscribe the {@link Agent} to
	 * the {@link Environment}, use
	 * {@link AbstractEnvironment#addAgent(AbstractAutonomousAgent)}.
	 * 
	 * @param agent
	 *            : to add
	 */
	public void addAgent(AbstractAutonomousAgent agent);

	/**
	 * Adds the given {@link ActiveBody} to this {@link Ambient}. This method
	 * should only be called outside of out the {@link Environment} cycle to
	 * prevent conflict of access. This method does not subscribe the
	 * {@link ActiveBody} to the {@link Environment}, use
	 * {@link AbstractEnvironment#addActiveBody(ActiveBody)}.
	 * 
	 * @param body
	 *            : to add
	 */
	public void addActiveBody(ActiveBody body);

	/**
	 * Adds the given {@link PassiveBody} to this {@link Ambient}. This method
	 * should only be called outside of out the {@link Environment} cycle to
	 * prevent conflict of access. This method does not subscribe the
	 * {@link PassiveBody} to the {@link Environment}, use
	 * {@link AbstractEnvironment#addAgent(AbstractAutonomousAgent)}.
	 * 
	 * @param body
	 *            : to add
	 */
	public void addPassiveBody(PassiveBody body);

	/**
	 * Adds the given {@link AbstractAvatarAgent} to this {@link Ambient}. This
	 * method should only be called outside of out the {@link Environment} cycle
	 * to prevent conflict of access. This method does not subscribe the
	 * {@link Avatar} to the {@link Environment}, use
	 * {@link AbstractEnvironment#addAgent(AbstractAutonomousAgent)}.
	 * 
	 * @param avatar
	 *            : to add
	 */
	public void addAvatar(AbstractAvatarAgent<?> avatar);

	public Collection<AbstractAvatarAgent<?>> getAvatars();

	public Collection<AbstractAutonomousAgent> getAgents();

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