package uk.ac.rhul.cs.dice.starworlds.entities.agent.concrete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.CommunicationAction;
import uk.ac.rhul.cs.dice.starworlds.actions.speech.DefaultPayload;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractAgentMind;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;

public class DefaultAgentMind extends AbstractAgentMind {

	private List<String> recipients = new ArrayList<>();

	public DefaultAgentMind() {
		recipients.add("0");
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

	@Override
	public Perception<?> perceive(Collection<Perception<?>> perceptions) {
		System.out.println("PERCEPTIONS: " + perceptions);
		return null;
	}

	@Override
	public Action decide(Perception<?> perception) {
		return new CommunicationAction<>(new DefaultPayload<String>("hello"), this.recipients);
	}

	@Override
	public Action execute(Action action) {
		return action;
	}
}
