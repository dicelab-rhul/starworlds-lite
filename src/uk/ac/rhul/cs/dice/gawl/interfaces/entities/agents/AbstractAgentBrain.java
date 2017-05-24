package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents;

import java.util.HashSet;
import java.util.Set;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Action;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.environmental.SensingAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.components.Actuator;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.components.Sensor;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.concrete.DefaultAgentBrain;
import uk.ac.rhul.cs.dice.gawl.interfaces.perception.Perception;

/**
 * The abstract class representing a {@link Brain}. {@link Perception}s are
 * stored here after every perceive. These may be retrieved by the mind by
 * calling {@link AbstractAgentBrain#getPerceptions()} and used in the decision
 * procedure. It should be noted that all {@link Perception} will be cleared
 * from the brain at the end of each cycle (i.e after execute has been called).
 * The execute method will attempt to execute any {@link Action}s that the mind
 * has provided during the decide phase, actions can be added using the
 * {@link AbstractAgentBrain#addActionToPerform(AbstractEnvironmentalAction)}
 * method. Actions will also be removed at the end of each cycle. <br/>
 * IMPORTANT NOTE: {@link AbstractAgentBrain#execute()} and
 * {@link AbstractAgentBrain#perceive()} should NEVER be called by anything
 * other than the physics that is currently managing the agent and should only
 * ever be called ONCE per cycle. <br/>
 * 
 * Known direct subclasses: {@link DefaultAgentBrain}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class AbstractAgentBrain implements Brain {

	// current perceptions of this agent received in perceive
	private Set<Perception<?>> perceptions;
	// actions to be performed in the next execute
	private Set<AbstractEnvironmentalAction> actions;

	// the mind associated with this brain
	private AbstractAgentMind mind;
	// the body associated with the body
	private AbstractAgent body;

	/**
	 * Constructor.
	 */
	public AbstractAgentBrain() {
		perceptions = new HashSet<>();
		actions = new HashSet<AbstractEnvironmentalAction>();
	}

	@Override
	public void perceive() {
		// get all perceptions
		this.body.getSensors().forEach((Sensor s) -> {
			perceptions.addAll(s.getPerceptions());
		});
	}

	@Override
	public void execute() {
		// send actions to their actuators
		actions.forEach((AbstractEnvironmentalAction action) -> {
			if (SensingAction.class.isAssignableFrom(action.getClass())) {
				Sensor s = body
						.getSensors()
						.stream()
						.filter((Sensor se) -> action.getActuator()
								.isAssignableFrom(se.getClass())).findFirst()
						.get();
				if (s != null) {

					s.activePerceive((SensingAction) action);
				} else {
					System.err
							.println("NO SENSOR IS AVALIABLE FOR SENSING ACTION: "
									+ action);
				}
			} else {
				Actuator a = body
						.getActuators()
						.stream()
						.filter((Actuator ac) -> action.getActuator()
								.isAssignableFrom(ac.getClass())).findFirst()
						.get();
				if (a != null) {
					a.performAction(action);
				} else {
					System.err.println("NO ACTUATOR IS AVALIABLE FOR ACTION: "
							+ action);
				}
			}
		});
		actions.clear();
		perceptions.clear();
	}

	public Set<Perception<?>> getPerceptions() {
		return this.perceptions;
	}

	@Override
	public void setMind(AbstractAgentMind mind) {
		if (this.mind == null) {
			this.mind = mind;
		}
	}

	@Override
	public void setBody(AbstractAgent body) {
		if (this.body == null) {
			this.body = body;
		}
	}

	@Override
	public AbstractAgent getBody() {
		return body;
	}

	@Override
	public AbstractAgentMind getMind() {
		return mind;
	}

	public void addActionToPerform(AbstractEnvironmentalAction action) {
		this.actions.add(action);
	}

}