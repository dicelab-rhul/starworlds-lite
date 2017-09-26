package uk.ac.rhul.cs.dice.starworlds.environment.physics;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.CommunicationAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.PhysicalAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SensingAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.ActiveBodyAppearance;
import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.Agent;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractAutonomousAgent;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.AbstractSensor;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.concrete.ListeningSensor;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.concrete.SeeingSensor;
import uk.ac.rhul.cs.dice.starworlds.entities.avatar.AbstractAvatarAgent;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.AbstractAmbient;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.Ambient;
import uk.ac.rhul.cs.dice.starworlds.environment.concrete.DefaultPhysics;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.AbstractEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.Environment;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.Simulator;
import uk.ac.rhul.cs.dice.starworlds.exceptions.StarWorldsException;
import uk.ac.rhul.cs.dice.starworlds.initialisation.ReflectiveMethodStore;
import uk.ac.rhul.cs.dice.starworlds.perception.AbstractPerception;
import uk.ac.rhul.cs.dice.starworlds.perception.ActivePerception;
import uk.ac.rhul.cs.dice.starworlds.perception.CommunicationPerception;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;

/**
 * An abstract implementation of {@link Physics}. This physics handles the
 * Perceive, Decide, Execute cycle of all {@link Agent}s in its
 * {@link Environment} - it is the time keeper. The {@link Physics} is
 * responsible for executing any {@link Action}s that an {@link Agent} or
 * {@link ActiveBody} performs. These {@link Action}s default as follows: </br>
 * {@link SensingAction}, {@link CommunicationAction}, {@link PhysicalAction}.
 * For details on each type see their documentation. </br>
 * 
 * 
 * Known direct subclasses: {@link DefaultPhysics}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class AbstractPhysics implements Physics, Simulator {

    private static final long DEFAULTFRAMELENGTH = 1000;
    private long framelength = DEFAULTFRAMELENGTH;
    protected AbstractEnvironment environment;
    protected AbstractAmbient state;

    // TODO change to not default
    private TimeState timestate = new TimeStateSerial();

    public AbstractPhysics() {
    }

    protected void sleep() {
	try {
	    Thread.sleep(framelength);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
    }

    public void simulate() {
	while (true) {
	    cycle();
	    sleep();
	}
    }

    protected void cycle() {
	this.runActors();
	this.executeActions();
	this.cycleAddition();
    }

    public abstract void cycleAddition();

    @Override
    public void runActors() {
	timestate.simulate();
    }

    @Override
    public void executeActions() {
	doPhysicalActions(environment.getState().flushPhysicalActions());
	doSensingActions(environment.getState().flushSensingActions());
	doCommunicationActions(environment.getState().flushCommunicationActions());
    }

    /**
     * The method used to notify {@link ActiveBody}s of their {@link Perception} .
     * It uses the
     * {@link AbstractEnvironment#notify(AbstractEnvironmentalAction, ActiveBodyAppearance, Collection, Ambient)
     * notify} method to do this, if this method is overridden, that is the method
     * that should be used.
     * 
     * @param action
     * @param context
     */
    protected void notifyPerceptions(AbstractEnvironmentalAction action, Ambient context) {
	Collection<AbstractPerception<?>> agentPerceptions = this.activeBodyPerceive(action.getActor(), action,
		context);
	if (agentPerceptions != null) {
	    notify(action, action.getActor(), agentPerceptions, context);
	}
    }

    public void notify(AbstractEnvironmentalAction action, ActiveBodyAppearance toNotify,
	    AbstractPerception<?> perception, Ambient context) {
	@SuppressWarnings("rawtypes")
	Map<Class<? extends AbstractPerception>, Set<AbstractSensor>> sensors = environment.getSubscriber()
		.findSensors(toNotify, action);
	notifySensors(sensors, perception, context);
    }

    public void notify(AbstractEnvironmentalAction action, ActiveBodyAppearance toNotify,
	    Collection<AbstractPerception<?>> perceptions, Ambient context) {
	@SuppressWarnings("rawtypes")
	Map<Class<? extends AbstractPerception>, Set<AbstractSensor>> sensors = environment.getSubscriber()
		.findSensors(toNotify, action);
	for (AbstractPerception<?> perception : perceptions) {
	    notifySensors(sensors, perception, context);
	}
    }

    private void notifySensors(
	    @SuppressWarnings("rawtypes") Map<Class<? extends AbstractPerception>, Set<AbstractSensor>> sensors,
	    AbstractPerception<?> perception, Ambient context) {
	Set<AbstractSensor> ss = sensors.get(perception.getClass());
	if (ss != null) {
	    for (AbstractSensor s : ss) {
		if (checkPerceivable(s, perception, context)) {
		    s.notify(perception);
		}
	    }
	}
    }

    protected boolean checkPerceivable(AbstractSensor sensor, AbstractPerception<?> perception, Ambient context) {
	try {
	    return (boolean) this.getClass().getMethod(ReflectiveMethodStore.PERCEIVABLE, sensor.getClass(),
		    AbstractPerception.class, Ambient.class).invoke(this, sensor, perception, context);
	} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException e) {
	    System.err.println("A Perceivable method must be defined in the class: " + this.getClass().getSimpleName()
		    + " and be accessible to the class: " + this.getClass().getSimpleName() + System.lineSeparator()
		    + "   It must have the signature: " + System.lineSeparator() + "   perceivable("
		    + sensor.getClass().getSimpleName() + "," + AbstractPerception.class.getSimpleName() + ","
		    + Ambient.class.getSimpleName() + ")");
	    e.printStackTrace();
	} catch (InvocationTargetException e) {
	    e.getTargetException().printStackTrace();
	}
	return false;
    }

    // ***************************************************** //
    // ****************** PHYSICAL ACTIONS ***************** //
    // ***************************************************** //

    protected void doPhysicalActions(Collection<PhysicalAction> actions) {
	Collection<PhysicalAction> failedactions = new ArrayList<>();
	/* execute all physical actions */
	actions.forEach((PhysicalAction a) -> {
	    if (!this.execute(a, environment.getState())) {
		failedactions.add(a);
	    }
	});
	/* notify perceptions of all physical actions */
	/* it is up to the agent to decide if the action failed? */
	for (PhysicalAction a : actions) {
	    this.notifyPerceptions(a, environment.getState());
	}
	/* remove all failed actions */
	actions.removeAll(failedactions);
	try {
	    /* notify the perceptions of all non-failed actions */
	    for (PhysicalAction a : actions) {
		this.notifyAgentPhysicalPerceptions(a, environment.getState());
		this.notifyOtherPhysicalPerceptions(a, environment.getState());
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    @Override
    public boolean execute(PhysicalAction action, Ambient context) {
	try {
	    if (isPossible(action, context)) {
		if (perform(action, context)) {
		    return verify(action, context);
		}
	    }
	} catch (Exception e) {
	    System.err.println("Something has gone wrong during the execution of the action: " + action);
	    e.printStackTrace();
	}
	return false;
    }

    /**
     * Notifies the {@link ActiveBody} has successfully performed a
     * {@link PhysicalAction} with some {@link Perception}s that is defined by the
     * specific {@link PhysicalAction}
     * {@link Physics#getAgentPerceptions(PhysicalAction, Ambient)} method.
     * 
     * @param action
     *            : that was performed
     * @param context
     *            : of the {@link Environment}
     * @throws StarWorldsException
     * @throws Exception
     *             : if something went wrong
     */
    protected void notifyAgentPhysicalPerceptions(PhysicalAction action, Ambient context) throws StarWorldsException {
	Collection<Object> temp = action.getAgentPerceptions(this, context);
	Collection<AbstractPerception<?>> agentPerceptions = temp.stream().filter(elm -> elm instanceof AbstractPerception<?>).map(elm -> (AbstractPerception<?>) elm).collect(Collectors.toList());

	if (agentPerceptions != null) {
	    notify(action, action.getActor(), agentPerceptions, context);
	}
    }

    /**
     * After a {@link PhysicalAction} has been successfully performed by an
     * {@link ActiveBody}, notify all other {@link ActiveBody ActiveBodies} with
     * some {@link Perception}s that is defined by the specific
     * {@link PhysicalAction}
     * {@link Physics#getOtherPerceptions(PhysicalAction, Ambient)} method.
     * 
     * @param action
     *            : that was performed
     * @param context
     *            : of the {@link Environment}
     * @throws Exception
     *             : if something went wrong
     */
    protected void notifyOtherPhysicalPerceptions(PhysicalAction action, Ambient context) throws StarWorldsException {
	Collection<Object> temp = action.getOtherPerceptions(this, context);
	Collection<AbstractPerception<?>> otherPerceptions = temp.stream().filter(elm -> elm instanceof AbstractPerception<?>).map(elm -> (AbstractPerception<?>) elm).collect(Collectors.toList());

	if (otherPerceptions != null) {
	    Collection<AbstractAutonomousAgent> others = new HashSet<>(state.getAgents());
	    others.remove(state.getAgent(action.getActor().getId()));

	    for (ActiveBody a : others) {
		notify(action, a.getAppearance(), otherPerceptions, context);
	    }
	}
    }

    @Override
    public boolean perform(PhysicalAction action, Ambient context) throws Exception {
	return action.perform(this, context);
    }

    @Override
    public boolean isPossible(PhysicalAction action, Ambient context) throws Exception {
	return action.isPossible(this, context);
    }

    @Override
    public boolean verify(PhysicalAction action, Ambient context) throws Exception {
	return action.verify(this, context);
    }

    // ***************************************************** //
    // ****************** SENSING ACTIONS ****************** //
    // ***************************************************** //

    protected void doSensingActions(Collection<SensingAction> actions) {
	/* execute all sensing actions */
	for (SensingAction a : actions) {
	    this.execute(a, environment.getState());
	}
	/*
	 * TODO in the future sense filtering should be optimised, it may be that many
	 * agents will be asking for the same filtered perception!
	 */
    }

    @Override
    public void execute(SensingAction action, Ambient context) {
	Map<String, Object> result = null;
	if (isPossible(action, context)) {
	    result = perform(action, context);
	    // verify(action, context); // TODO what to verify?
	}
	if (result != null) {
	    ActivePerception perception = new ActivePerception(result);
	    notify(action, action.getActor(), perception, context);
	} else {
	    /* if the sensing action failed, notify with the default */
	    notifyPerceptions(action, context);
	}
    }

    @Override
    public Map<String, Object> perform(SensingAction action, Ambient context) {
	return context.queryActivePerception(action.getKeys(), action);
    }

    @Override
    public boolean isPossible(SensingAction action, Ambient context) {
	/*
	 * checks each key given in the action to see if it exists. If the key does not
	 * exist, it is changed to null, a null key will be discarded later. This method
	 * will return true (i.e. the action is possible) if there is at least one valid
	 * key.
	 */
	String[] keys = action.getKeys();
	int count = 0;
	for (int i = 0; i < keys.length; i++) {
	    String[] subkeys = keys[i].split("\\.");
	    // TODO handle integer parameters
	    if (context.environmentAmbientAttribute(subkeys[0])) {
		for (int j = 1; j < subkeys.length; j++) {
		    if (!context.queryExists(subkeys[j])) {
			keys[i] = null;
			count++;
			break;
		    }
		}
	    } else {
		keys[i] = null;
		count++;
	    }
	}
	return count < keys.length;
    }

    /**
     * Not currently used by the execution procedure of {@link SensingAction}s.
     */
    @Override
    public boolean verify(SensingAction action, Ambient context) {
	return true;
    }

    // ***************************************************** //
    // *************** COMMUNICATION ACTIONS *************** //
    // ***************************************************** //

    protected void doCommunicationActions(Collection<CommunicationAction<?>> actions) {
	actions.forEach((CommunicationAction<?> c) -> this.execute(c, environment.getState()));
    }

    @Override
    public void execute(CommunicationAction<?> action, Ambient context) {
	if (isPossible(action, context)) {
	    perform(action, context);
	    verify(action, context);
	}
	/* notify the sending agent with a perception */
	notifyPerceptions(action, context);
    }

    @Override
    public boolean perform(CommunicationAction<?> action, Ambient context) {
	if (action.getRecipientsIds().isEmpty()) {
	    // send to all agents except the sender in the local environment
	    Collection<AbstractAutonomousAgent> recipients = new HashSet<>(state.getAgents());
	    recipients.remove(state.getAgent(action.getActor().getId()));
	    for (AbstractAutonomousAgent a : recipients) {
		notify(action, a.getAppearance(), new CommunicationPerception<>(action.getPayload()), context);
	    }
	} else {
	    CommunicationAction<?> localaction = new CommunicationAction<>(action);
	    localaction.setLocalEnvironment(this.environment.getAppearance());
	    List<String> recipients = localaction.getRecipientsIds();
	    for (String recipient : recipients) {
		AbstractAutonomousAgent agent;
		if ((agent = state.getAgent(recipient)) != null) {
		    notify(localaction, agent.getAppearance(), new CommunicationPerception<>(localaction.getPayload()),
			    context);
		}
	    }
	}
	return true;
    }

    @Override
    public boolean isPossible(CommunicationAction<?> action, Ambient context) {
	// TODO check if the message is for sub/super environments
	return true;
    }

    /**
     * Not currently used in the {@link CommunicationAction} execution procedure.
     */
    @Override
    public boolean verify(CommunicationAction<?> action, Ambient context) {
	return true;
    }

    @Override
    public void setEnvironment(AbstractEnvironment environment) {
	if (this.environment == null) {
	    this.environment = environment;
	    this.state = environment.getState();
	}
    }

    protected AbstractEnvironment getEnvironment() {
	return this.environment;
    }

    /**
     * The state of a {@link Physics} that may be either serial or parallel see
     * {@link TimeStateSerial}, {@link TimeStateParallel}. These {@link TimeState}s
     * define the order in which the {@link Agent}s should run, namely whether the
     * system is multi-threaded.
     * 
     * @author Ben Wilkins
     *
     */
    protected interface TimeState {
	public void start();

	public void simulate();
    }

    /**
     * The implementation of {@link TimeState} for serial, agents will run in an
     * arbitrary order one at a time.
     * 
     * @author Ben Wilkins
     *
     */
    protected class TimeStateSerial implements TimeState {

	public void simulate() {
	    state.getAvatars().forEach((AbstractAvatarAgent<?> a) -> a.run());
	    state.getAgents().forEach((AbstractAutonomousAgent a) -> a.run());
	    state.getActiveBodies().forEach((ActiveBody a) -> a.run());
	}

	@Override
	public void start() {

	}
    }

    /**
     * The implementation of {@link TimeState} for parallel, agents will run in
     * their own thread in parallel.
     * 
     * @author Ben Wilkins
     *
     */
    protected class TimeStateParallel implements TimeState {

	@Override
	public void start() {

	}

	@Override
	public void simulate() {
	    // split into threads
	    Collection<Thread> threads = new ArrayList<>();
	    getThreads(state.getAvatars());
	    getThreads(state.getAgents());
	    getThreads(state.getActiveBodies());
	    waitForActors(threads);
	}

	private Collection<Thread> getThreads(Collection<? extends Runnable> runnables) {
	    Collection<Thread> threads = new ArrayList<>();
	    runnables.forEach((Runnable a) -> {
		Thread t = new Thread(a);
		threads.add(t);
		t.start();
	    });
	    return threads;
	}

	private void waitForActors(Collection<Thread> threads) {
	    try {
		for (Thread t : threads)
		    t.join();
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
    }

    // TODO check class hierarchy, do perceivable methods properly

    /**
     * Perceivable method for all {@link SeeingSensor}s. See
     * {@link Physics#perceivable(AbstractSensor, AbstractPerception, Ambient)}.
     */
    public boolean perceivable(SeeingSensor sensor, AbstractPerception<?> perception, Ambient context) {
	return true;
    }

    /**
     * Perceivable method for all {@link ListeningSensor}s. See
     * {@link Physics#perceivable(AbstractSensor, AbstractPerception, Ambient)}.
     */
    public boolean perceivable(ListeningSensor sensor, AbstractPerception<?> perception, Ambient context) {
	return true;
    }

    @Override
    public boolean perceivable(AbstractSensor sensor, AbstractPerception<?> perception, Ambient context) {
	return true;
    }

    /**
     * Returns the id: "P" + {@link Environment} id that this {@link Physics}
     * resides in.
     * 
     * @return the inherited id
     */
    @Override
    public String getId() {
	return "P" + environment.getId();
    }

    /**
     * Unused, a physics inherits its id from its {@link Environment}.
     */
    @Override
    public void setId(String id) {
	// cannot set the id of a physics
    }

    protected void setTimeState(boolean serial) {
	if (serial) {
	    timestate = new TimeStateSerial();
	} else {
	    timestate = new TimeStateParallel();
	}
    }

    public void setFramelength(long framelength) {
	this.framelength = framelength;
    }

    public long getFramelength() {
	return framelength;
    }

    @Override
    public void run() {
	this.simulate();
    }
}