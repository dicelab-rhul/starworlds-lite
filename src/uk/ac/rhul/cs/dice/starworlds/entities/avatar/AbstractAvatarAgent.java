package uk.ac.rhul.cs.dice.starworlds.entities.avatar;

import java.util.Collection;
import java.util.List;
import java.util.Stack;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.ActiveBodyAppearance;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractMind;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractAgent;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Actuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Sensor;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;

public abstract class AbstractAvatarAgent<D extends Action> extends
		AbstractAgent<D, Stack<D>> implements Avatar {

	public AbstractAvatarAgent(List<Sensor> sensors, List<Actuator> actuators,
			AbstractMind<D, Stack<D>> mind) {
		super(sensors, actuators, mind);
	}

	public AbstractAvatarAgent(ActiveBodyAppearance appearance,
			List<Sensor> sensors, List<Actuator> actuators,
			AbstractMind<D, Stack<D>> mind) {
		super(appearance, sensors, actuators, mind);
	}

	@Override
	public AbstractAvatarMind<D> getMind() {
		return (AbstractAvatarMind<D>) super.getMind();
	}

	@Override
	public void run() {
		// get perceptions from sensors
		Collection<Perception<?>> sensoryPerceptions = this.perceive();
		// call mind cycle
		Action actionToExecute = this.getMind().cycle(sensoryPerceptions);
		// activate actuators
		this.doAct((AbstractEnvironmentalAction) actionToExecute);
		// execute any actions to perform in actuators
		this.execute((AbstractEnvironmentalAction) actionToExecute);
	}
}
