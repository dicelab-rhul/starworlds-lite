package uk.ac.rhul.cs.dice.starworlds.experiments.synchronisationexperiment;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.CommunicationAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SensingAction;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractAgentMind;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;

public class TestAgentMind extends AbstractAgentMind {

	boolean first = false;

	@Override
	public Perception<?> perceive(Collection<Perception<?>> perceptions) {
		System.out.println("PERCEPTIONS: " + perceptions);
		return null;
	}

	@Override
	public Action decide(Perception<?> perception) {
		if (first) {
			return new SensingAction("AGENTS.APPEARANCE");
		}
		return new CommunicationAction<String>("hello from:"
				+ this.getId(), null);
	}

	@Override
	public Action execute(Action action) {
		return action;
	}
}
