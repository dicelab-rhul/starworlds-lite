package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.concrete;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgentMind;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.components.concrete.SeeingSensor;

public class DefaultAgentMind extends AbstractAgentMind {

	private List<String> recipients = new ArrayList<>();

	public DefaultAgentMind() {
		recipients.add("0");
	}

	private Random rand = new Random();

	@Override
	public void decide() {
		if (rand.nextFloat() < 0) {
			communicate("Hello", recipients);
		} else {
			// get all the agents in this environment.
			// Note that agent permissions will be added later!
			sense(SeeingSensor.class, "AGENTS", "NOTHING!");
		}
		System.out.println("MIND PERCEPTIONS: "
				+ Arrays.toString(this.getBrain().getPerceptions().toArray()));

	}
	/*
	 * Do Speak Sense - look listen smell touch
	 */
}
