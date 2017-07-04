package uk.ac.rhul.cs.dice.starworlds.experiment.physicalagents;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractAgentMind;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;

public class RandomPhysicalAgentMind extends AbstractAgentMind {

	private Random r = new Random();
	private boolean start = true;

	@Override
	public Perception<?> perceive(Collection<Perception<?>> perceptions) {
		System.out.println(this.getId() + " :PERCEPTIONS: " + perceptions);
		return null;
	}

	@Override
	public Action decide(Perception<?> perception) {
		Action action = new MoveAction(r.nextInt(3) - 1, r.nextInt(3) - 1);
		System.out.println(this.getId() + " :ACTION: " + action);
		return action;
	}

	@Override
	public Action execute(Action action) {
		return action;
	}

}
