package uk.ac.rhul.cs.dice.starworlds.experiment.communicatingagents;

import java.util.HashSet;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.CommunicationAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SensingAction;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.starworlds.environment.DistributedUniverse;
import uk.ac.rhul.cs.dice.starworlds.environment.Universe;
import uk.ac.rhul.cs.dice.starworlds.environment.concrete.DefaultState;
import uk.ac.rhul.cs.dice.starworlds.experiment.ExperimentPhysics;
import uk.ac.rhul.cs.dice.starworlds.initialisation.AgentFactory;

class ExperimentCommunication {

	private final static int NUMAGENTS = 2;

	private static boolean serial = false;
	private static int port = 10001;

	public static void main(String[] args) {
		Set<Class<? extends AbstractEnvironmentalAction>> possibleActions = new HashSet<>();
		possibleActions.add(CommunicationAction.class);
		possibleActions.add(SensingAction.class);

		ExperimentPhysics physics = new ExperimentPhysics(
				getDefaultAgents(NUMAGENTS), null, null);
		createUniverse(physics, possibleActions);
		physics.start(serial);
	}

	public static DistributedUniverse createDistributedUniverse(
			ExperimentPhysics physics,
			Set<Class<? extends AbstractEnvironmentalAction>> possibleActions,
			int port) {
		return new DistributedUniverse(new DefaultState(physics), physics,
				null, possibleActions, port);
	}

	public static Universe createUniverse(ExperimentPhysics physics,
			Set<Class<? extends AbstractEnvironmentalAction>> possibleActions) {
		return new Universe(new DefaultState(physics), physics, null,
				possibleActions);
	}

	public static Set<AbstractAgent> getDefaultAgents(int num) {
		Set<AbstractAgent> agents = new HashSet<>();
		for (int i = 0; i < num; i++) {
			AbstractAgent a = AgentFactory.getInstance().createDefaultAgent(
					new RandomCommunicatingAgentMind());
			agents.add(a);
			a.setId(String.valueOf(i));
		}
		return agents;
	}
}
