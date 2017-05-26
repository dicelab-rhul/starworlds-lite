package uk.ac.rhul.cs.dice.starworlds.experiment.communicatingagents;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.CommunicationAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SensingAction;
import uk.ac.rhul.cs.dice.starworlds.actions.speech.Payload;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgentMind;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.concrete.DefaultAgent;
import uk.ac.rhul.cs.dice.starworlds.perception.CommunicationPerception;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;
import uk.ac.rhul.cs.dice.starworlds.utils.Pair;

class RandomCommunicatingAgentMind extends AbstractAgentMind {

	private boolean start = true;
	private List<String> otheragents = new ArrayList<>();

	@Override
	public Perception<?> perceive(Object... parameters) {
		Collection<Perception<?>> perceptions = super
				.unpackPerceptions(parameters);
		for (Perception<?> p : perceptions) {
			if (CommunicationPerception.class.isAssignableFrom(p.getClass())) {
				// unpack the message and add it to the list of agents!
				Payload<?> payload = (Payload<?>) p.getPerception();
				// System.out.println("MESSAGE PAYLOAD: " + payload);
				otheragents.add((String) payload.getPayload());
			} else {
				unpackActivePerception(p)
						.forEach(
								(Pair<?, ?> pair) -> otheragents
										.add(((DefaultAgent) pair.getSecond())
												.getId()));
			}
		}
		//System.out.println(this + " PERCEPTIONS: " + perceptions);
		// no need to return as we are saving the state
		return null;
	}

	@Override
	public Collection<Action> decide(Object... parameters) {
		Collection<Action> actions = new HashSet<>();
		if (!start) {
			Optional<String> recipient = otheragents.stream()
					.skip((long) (Math.random() * otheragents.size())).findAny();
			if (recipient != null) {
				List<String> recipients = new ArrayList<>();
				recipients.add(recipient.get());
				actions.add(new CommunicationAction<>(this.getId(), recipients));
				 //System.out.println(this + " DECISIONS: " + actions);
				return actions;
			}
			// the sensing action must have fail for some reason, so do it again
		}
		actions.add(new SensingAction("AGENTS.RANDOM"));
		start = false;
		 //System.out.println(this + " DECISIONS: " + actions);
		return actions;
	}

	@Override
	public Action execute(Object... parameters) {
		Collection<?> actions = (Collection<?>) parameters[0];
		Action action = doAct((AbstractEnvironmentalAction) actions.stream()
				.findFirst().get());
		 //System.out.println(this + " EXECUTE: " + action);
		return action;
	}

	private Set<Pair<?, ?>> unpackActivePerception(Perception<?> perception) {
		Set<Pair<?, ?>> result = new HashSet<>();
		((Set<?>) perception.getPerception()).forEach((Object o) -> {
			result.add((Pair<?, ?>) o);
		});
		return result;
	}
}
