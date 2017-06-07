package uk.ac.rhul.cs.dice.starworlds.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.CommunicationAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SensingAction;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Sensor;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.concrete.ListeningSensor;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.concrete.DefaultAgentMind;
import uk.ac.rhul.cs.dice.starworlds.environment.Universe;
import uk.ac.rhul.cs.dice.starworlds.environment.concrete.DefaultPhysics;
import uk.ac.rhul.cs.dice.starworlds.environment.concrete.DefaultState;
import uk.ac.rhul.cs.dice.starworlds.experiment.physicalagents.BadSeeingSensor;
import uk.ac.rhul.cs.dice.starworlds.initialisation.AgentFactory;

public class TestServerEnvironment {

	private final static int NUMAGENTS = 2;

	public static void main(String[] args) {
		Set<Class<? extends AbstractEnvironmentalAction>> possibleActions = new HashSet<>();
		possibleActions.add(CommunicationAction.class);
		possibleActions.add(SensingAction.class);

		DefaultPhysics physics = new DefaultPhysics(
				getDefaultAgents(NUMAGENTS), null, null);
		new Universe(new DefaultState(physics), physics, null, possibleActions);
		physics.start(false);
	}

	public static Set<AbstractAgent> getDefaultAgents(int num) {
		Set<AbstractAgent> agents = new HashSet<>();
		for (int i = 0; i < num; i++) {
			List<Sensor> sensors = new ArrayList<>();
			sensors.add(new ListeningSensor());
			sensors.add(new BadSeeingSensor());
			AbstractAgent a = AgentFactory.getInstance()
					.createCustomDefaultAgent(null, null,
							new DefaultAgentMind());
			agents.add(a);
		}
		return agents;
	}

}
