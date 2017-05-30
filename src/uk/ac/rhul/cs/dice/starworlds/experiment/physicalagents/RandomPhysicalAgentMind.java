package uk.ac.rhul.cs.dice.starworlds.experiment.physicalagents;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SensingAction;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgentMind;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;

class RandomPhysicalAgentMind extends AbstractAgentMind {

	private Random r = new Random();
	private boolean start = true;

	@Override
	public Perception<?> perceive(Object... parameters) {
		Collection<Perception<?>> perceptions = super
				.unpackPerceptions(parameters);
		System.out.println(this.getId() + " :PERCEPTIONS: " + perceptions);
		return null;
	}

	@Override
	public Collection<Action> decide(Object... parameters) {
		if (start) {
			start = false;
			this.getBody().subscribeAll();
		}
		Collection<Action> actions = new HashSet<>();
		actions.add(new MoveAction(r.nextInt(3) - 1, r.nextInt(3) - 1));
		// actions.add(new SensingAction("GRID.LOCAL"));
		System.out.println(this.getId() + ":ACTIONS: " + actions);
		return actions;
	}

	@Override
	public Action execute(Object... parameters) {
		Collection<?> actions = (Collection<?>) parameters[0];
		Action action = doAct((AbstractEnvironmentalAction) actions.stream()
				.findFirst().get());
		// System.out.println(this + " EXECUTE: " + action);
		return action;
	}

}
