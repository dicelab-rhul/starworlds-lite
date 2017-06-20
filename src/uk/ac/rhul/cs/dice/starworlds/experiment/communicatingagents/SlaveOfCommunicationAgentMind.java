package uk.ac.rhul.cs.dice.starworlds.experiment.communicatingagents;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.CommunicationAction;

public class SlaveOfCommunicationAgentMind extends RandomCommunicatingAgentMind {

	@Override
	public Collection<Action> decide(Object... parameters) {
		Collection<Action> actions = new HashSet<>();
		Optional<String> recipient = otheragents.stream()
				.skip((long) (Math.random() * otheragents.size())).findAny();
		List<String> recipients = new ArrayList<>();
		if (recipient.isPresent()) {
			recipients.add(recipient.get());
			actions.add(new CommunicationAction<>(this.getId(), recipients));
		}
		System.out.println(this + " DECISIONS: " + actions);
		return actions;
	}
}
