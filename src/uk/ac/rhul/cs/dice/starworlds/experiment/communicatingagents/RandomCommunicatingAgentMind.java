package uk.ac.rhul.cs.dice.starworlds.experiment.communicatingagents;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.CommunicationAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SensingAction;
import uk.ac.rhul.cs.dice.starworlds.actions.speech.Payload;
import uk.ac.rhul.cs.dice.starworlds.appearances.Appearance;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgentMind;
import uk.ac.rhul.cs.dice.starworlds.perception.CommunicationPerception;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;
import uk.ac.rhul.cs.dice.starworlds.utils.Pair;

public class RandomCommunicatingAgentMind extends AbstractAgentMind {

	protected boolean start = true;
	protected List<String> otheragents = new ArrayList<>();

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
				if (p.getPerception() != null) {
					Collection<Appearance> appearances = unpackAppearances(p);
					for (Appearance a : appearances) {
						this.otheragents.add(a.getId());
					}
				}
			}
		}
		System.out.println(this + " PERCEPTIONS: " + perceptions);
		// no need to return as we are saving the state
		return null;
	}

	@Override
	public Action decide(Object... parameters) {
		if (!start) {
			Optional<String> recipient = otheragents.stream()
					.skip((long) (Math.random() * otheragents.size()))
					.findAny();
			if (recipient != null) {
				List<String> recipients = new ArrayList<>();
				recipients.add(otheragents.stream()
						.skip((long) (Math.random() * otheragents.size()))
						.findFirst().get());
				return new CommunicationAction<>(this.getId(), recipients);
			}
			// the sensing action must have fail for some reason, so do it again
		}
		start = false;
		return new SensingAction("AGENTS.APPEARANCE.RANDOM");
	}

	@Override
	public Action execute(Object... parameters) {
		Action action = unpackAction(parameters);
		return doAct((AbstractEnvironmentalAction) action);
	}

	private Collection<Appearance> unpackAppearances(Perception<?> percept) {
		Collection<Appearance> appearances = new ArrayList<>();
		Pair<?, ?> pair = (Pair<?, ?>) percept.getPerception();
		if (pair.getSecond() != null) {
			appearances.add((Appearance) pair.getSecond());
		}
		return appearances;
	}
}
