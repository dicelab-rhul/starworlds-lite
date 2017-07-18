package uk.ac.rhul.cs.dice.starworlds.experiment.communicatingagents;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.CommunicationAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SensingAction;
import uk.ac.rhul.cs.dice.starworlds.actions.speech.Payload;
import uk.ac.rhul.cs.dice.starworlds.appearances.Appearance;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractAgentMind;
import uk.ac.rhul.cs.dice.starworlds.perception.CommunicationPerception;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;

public class RandomCommunicatingAgentMind extends AbstractAgentMind {

	protected boolean start = true;
	protected List<String> otheragents = new ArrayList<>();

	@Override
	public Perception<?> perceive(Collection<Perception<?>> perceptions) {
		// System.out.println(this + " PERCEPTIONS: " + perceptions);
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
		// no need to return as we are saving the state
		return null;
	}

	@Override
	public Action decide(Perception<?> perception) {
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
	public Action execute(Action action) {
		return action;
	}

	private Collection<Appearance> unpackAppearances(Perception<?> percept) {
		Collection<Appearance> appearances = new ArrayList<>();
		HashMap<?, ?> keymap = (HashMap<?, ?>) percept.getPerception();
		keymap.forEach((key, value) -> {
			System.out.println(key + "->" + value);
			if (value != null) {
				appearances.add((Appearance) value);
			}
		});
		return appearances;
	}
}
