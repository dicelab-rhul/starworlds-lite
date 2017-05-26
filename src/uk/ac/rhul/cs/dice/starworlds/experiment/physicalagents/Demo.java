package uk.ac.rhul.cs.dice.starworlds.experiment.physicalagents;

import java.util.HashSet;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.starworlds.environment.concrete.DefaultEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.concrete.DefaultPhysics;
import uk.ac.rhul.cs.dice.starworlds.environment.concrete.DefaultState;
import uk.ac.rhul.cs.dice.starworlds.initialisation.AgentFactory;

public class Demo {

	private final static int NUMAGENTS = 2;

	public static void main(String[] args) {
		DefaultPhysics physics = new DefaultPhysics(
				getDefaultAgents(NUMAGENTS), null, null);
		new DefaultEnvironment(new DefaultState(physics), physics, null);
		physics.start();
	}

	public static Set<AbstractAgent> getDefaultAgents(int num) {
		Set<AbstractAgent> agents = new HashSet<>();
		for (int i = 0; i < num; i++) {
			AbstractAgent a = AgentFactory.getInstance().createDefaultAgent(
					null, new RandomPhysicalAgentMind());
			agents.add(a);
		}
		return agents;
	}

}
