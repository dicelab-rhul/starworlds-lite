package uk.ac.rhul.cs.dice.starworlds.entities.agents.concrete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.CommunicationAction;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgentMind;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;

public class DefaultAgentMind extends AbstractAgentMind {

	private List<String> recipients = new ArrayList<>();

	public DefaultAgentMind() {
		recipients.add("0");
	}

	@Override
	public Perception<?> perceive(Object... parameters) {
		Collection<?> perceptions = (Collection<?>) parameters[0];
		System.out.println("PERCEPTIONS: " + perceptions);
		return null;
	}

	@Override
	public Collection<Action> decide(Object... parameters) {
		Collection<Action> actions = new HashSet<>();
		actions.add(new CommunicationAction<>("hello", this.recipients));
		System.out.println("DECISIONS: " + actions);
		return actions;
	}

	@Override
	public Action execute(Object... parameters) {
		Collection<?> actions = (Collection<?>) parameters[0];
		Action action = (Action) actions.stream().findFirst().get();
		System.out.println("EXECUTE: " + action);
		return super.doSpeakAct((CommunicationAction<?>) action);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
