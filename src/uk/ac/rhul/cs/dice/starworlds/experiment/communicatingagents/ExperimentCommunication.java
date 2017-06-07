package uk.ac.rhul.cs.dice.starworlds.experiment.communicatingagents;

import java.util.HashSet;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.CommunicationAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SensingAction;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.starworlds.environment.DistributedUniverse;
import uk.ac.rhul.cs.dice.starworlds.environment.SimpleEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.Universe;
import uk.ac.rhul.cs.dice.starworlds.environment.base.AbstractEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.concrete.DefaultState;
import uk.ac.rhul.cs.dice.starworlds.experiment.ExperimentPhysics;
import uk.ac.rhul.cs.dice.starworlds.initialisation.AgentFactory;

class ExperimentCommunication {

	private final static int NUMAGENTS = 1;
	private final static boolean distributed = false;

	private static boolean serial = false;
	private static int port1 = 10000;
	private static int port2 = 20000;

	public static void main(String[] args) {
		Set<Class<? extends AbstractEnvironmentalAction>> possibleActions = new HashSet<>();
		possibleActions.add(CommunicationAction.class);
		possibleActions.add(SensingAction.class);

		if (distributed) {
			doDistributed(possibleActions);
		} else {
			doLocalSingleSubenvironment(possibleActions);
		}
	}

	private static void doLocalSingleSubenvironment(
			Set<Class<? extends AbstractEnvironmentalAction>> possibleActions) {
		ExperimentPhysics physics = new ExperimentPhysics(
				getDefaultAgents(NUMAGENTS), null, null, "1");
		Universe universe = createUniverse(physics, possibleActions,
				createSimpleEnvironment(1, 2, possibleActions));
		universe.simulate();
	}

	public static SimpleEnvironment createSimpleEnvironment(int numagents,
			int id,
			Set<Class<? extends AbstractEnvironmentalAction>> possibleActions) {
		ExperimentPhysics physics = new ExperimentPhysics(
				getDefaultAgents(numagents), null, null, String.valueOf(id));
		return new SimpleEnvironment(new DefaultState(physics), physics, null,
				possibleActions);
	}

	private static void doLocal(
			Set<Class<? extends AbstractEnvironmentalAction>> possibleActions) {
		ExperimentPhysics physics = new ExperimentPhysics(
				getDefaultAgents(NUMAGENTS), null, null, "1");
		createUniverse(physics, possibleActions);
		//physics.start(serial);
	}

	private static void doDistributed(
			Set<Class<? extends AbstractEnvironmentalAction>> possibleActions) {
		ExperimentPhysics physics1 = new ExperimentPhysics(
				getDefaultAgents(NUMAGENTS), null, null, "1");
		ExperimentPhysics physics2 = new ExperimentPhysics(
				getDefaultAgents(NUMAGENTS), null, null, "2");
		DistributedUniverse universe1 = createDistributedUniverse(physics1,
				possibleActions, port1);
		DistributedUniverse universe2 = createDistributedUniverse(physics2,
				possibleActions, port2);
		universe1.connectToEnvironment("localhost", port2);
//		physics1.start(serial);
//		physics2.start(serial);
	}

	public static DistributedUniverse createDistributedUniverse(
			ExperimentPhysics physics,
			Set<Class<? extends AbstractEnvironmentalAction>> possibleActions,
			int port) {
		return new DistributedCommunicationUniverse(new DefaultState(physics),
				physics, null, possibleActions, port);
	}

	public static Universe createUniverse(ExperimentPhysics physics,
			Set<Class<? extends AbstractEnvironmentalAction>> possibleActions,
			AbstractEnvironment... subenvironments) {
		return new Universe(new DefaultState(physics), physics, null,
				possibleActions, subenvironments);
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
