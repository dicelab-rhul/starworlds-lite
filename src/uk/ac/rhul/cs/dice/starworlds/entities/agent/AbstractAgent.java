package uk.ac.rhul.cs.dice.starworlds.entities.agent;

import java.util.Collection;
import java.util.List;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.actions.MentalAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.ActiveBodyAppearance;
import uk.ac.rhul.cs.dice.starworlds.entities.Agent;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Actuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Sensor;
import uk.ac.rhul.cs.dice.starworlds.parser.DefaultConstructorStore.DefaultConstructor;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;

public abstract class AbstractAgent extends
		AbstractMindfulActiveBody<Perception<?>, Action> implements Agent {

	/**
	 * Constructor.
	 * 
	 * @param sensors
	 *            : a {@link List} of {@link Sensor} instances.
	 * @param actuators
	 *            : a {@link List} of {@link Actuator} instances.
	 * @param mind
	 *            : the {@link AbstractAgentMind}.
	 * 
	 */
	@DefaultConstructor
	public AbstractAgent(List<Sensor> sensors, List<Actuator> actuators,
			AbstractAgentMind mind) {
		super(null, sensors, actuators, mind);
		init(mind);
		this.setAppearance(new ActiveBodyAppearance(this));
	}

	/**
	 * Constructor.
	 * 
	 * @param appearance
	 *            : the {@link ActiveBodyAppearance}.
	 * @param sensors
	 *            : a {@link List} of {@link Sensor} instances.
	 * @param actuators
	 *            : a {@link List} of {@link Actuator} instances.
	 * @param mind
	 *            : the {@link AbstractAgentMind}.
	 */
	public AbstractAgent(ActiveBodyAppearance appearance, List<Sensor> sensors,
			List<Actuator> actuators, AbstractAgentMind mind) {
		super(appearance, sensors, actuators, mind);
		init(mind);
	}

	private void init(AbstractAgentMind mind) {
		this.mind = mind;
		this.mind.setBody(this);
	}

	/**
	 * Returns the {@link Mind} of the {@link AbstractAgent}.
	 * 
	 * @return the {@link Mind} of the {@link AbstractAgent}.
	 */
	@Override
	public AbstractAgentMind getMind() {
		return (AbstractAgentMind) this.mind;
	}

	@Override
	public void run() {
		// get perceptions from sensors
		Collection<Perception<?>> sensoryPerceptions = this.perceive();
		// call mind cycle
		Action actionToExecute = this.getMind().cycle(sensoryPerceptions);
		this.doAct((AbstractEnvironmentalAction) actionToExecute);
		// execute any actions to perform in actuators
		if (actionToExecute != null) {
			if (!MentalAction.class
					.isAssignableFrom(actionToExecute.getClass())) {
				this.execute((AbstractEnvironmentalAction) actionToExecute);
			}
		}
	}
}