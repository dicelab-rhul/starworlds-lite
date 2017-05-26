package uk.ac.rhul.cs.dice.starworlds.entities.agents;

import java.net.Socket;
import java.util.Collection;
import java.util.List;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.actions.MentalAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.AbstractAppearance;
import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.Agent;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Actuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Sensor;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;

/**
 * A subclass of {@link ActiveBody} implementing the {@link Agent} interface. It
 * contains a {@link Brain} and a {@link Mind}.<br/>
 * <br/>
 * 
 * Known direct subclasses: {@link AutonomousAgent}, {@link Avatar}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class AbstractAgent extends ActiveBody implements Agent {

	private AbstractAgentMind mind;
	private Socket socket; // TODO

	/**
	 * The class constructor.
	 * 
	 * @param appearance
	 *            : the {@link AbstractAgentAppearance}.
	 * @param sensors
	 *            : a {@link List} of {@link Sensor} instances.
	 * @param actuators
	 *            : a {@link List} of {@link Actuator} instances.
	 * @param mind
	 *            : the {@link AbstractAgentMind}.
	 * @param brain
	 *            : the {@link AbstractAgentBrain}.
	 */
	public AbstractAgent(AbstractAppearance appearance, List<Sensor> sensors,
			List<Actuator> actuators, AbstractAgentMind mind) {
		super(appearance, sensors, actuators);
		this.mind = mind;
		this.mind.setBody(this);

	}

	/**
	 * Returns the {@link Brain} of the {@link AbstractAgent}.
	 * 
	 * @return the {@link Brain} of the {@link AbstractAgent}.
	 */

	/**
	 * Returns the {@link Mind} of the {@link AbstractAgent}.
	 * 
	 * @return the {@link Mind} of the {@link AbstractAgent}.
	 */
	@Override
	public AbstractAgentMind getMind() {
		return this.mind;
	}

	@Override
	public void run() {
		// get perceptions from sensors
		Collection<Perception<?>> sensoryPerceptions = this.perceive();
		// call mind cycle
		Action actionToExecute = (Action) this.mind.cycle(sensoryPerceptions)[0];
		// execute any actions to perform in actuators
		if (actionToExecute != null) {
			if (!MentalAction.class
					.isAssignableFrom(actionToExecute.getClass())) {
				this.execute((AbstractEnvironmentalAction) actionToExecute);
			}
		}
	}
}