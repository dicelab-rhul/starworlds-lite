package uk.ac.rhul.cs.dice.starworlds.experiments.synchronisationexperiment;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.CommunicationAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SensingAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.EnvironmentAppearance;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractAutonomousAgent;
import uk.ac.rhul.cs.dice.starworlds.environment.concrete.DefaultAmbient;
import uk.ac.rhul.cs.dice.starworlds.environment.concrete.DefaultWorld;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.AbstractConnectedEnvironment.AmbientRelation;
import uk.ac.rhul.cs.dice.starworlds.initialisation.AgentFactory;
import uk.ac.rhul.cs.dice.starworlds.initialisation.WorldDeployer;

public class SynchronisationExperiment {

	private static AgentFactory FACTORY = AgentFactory.getInstance();
	// The set of actions possible in the Environments
	private static Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions = new HashSet<>();
	static {
		possibleActions.add(SensingAction.class);
		possibleActions.add(CommunicationAction.class);
	}

	public static void main(String[] args) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				WorldDeployer.deployAndRun(getServer());
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				WorldDeployer.deployAndRun(getClient());
			}
		}).start();
	}

	public static DefaultWorld getClient() {
		Integer port = 10001;
		Integer portremote = 10002;
		String addr = "localhost";
		DefaultWorld world = new DefaultWorld(new TestUniverse(port,
				new DefaultAmbient(getAgents(0), null, null, null),
				new TestPhysics(), new EnvironmentAppearance("Client", false,
						false), possibleActions));
		world.getRoot()
				.getValue()
				.addRemoteConnection(addr, portremote,
						AmbientRelation.NEIGHBOUR);
		return world;
	}

	public static DefaultWorld getServer() {
		Integer expectedConnections = 1;
		Integer port = 10002;
		DefaultWorld world = new DefaultWorld(new TestUniverse(port,
				new DefaultAmbient(getAgents(0), null, null, null),
				new TestPhysics(), new EnvironmentAppearance("Server", false,
						false), possibleActions));
		world.getRoot().getValue()
				.setExpectedRemoteConnections(expectedConnections);
		return world;
	}

	// creates some default communicating agents
	private static Set<AbstractAutonomousAgent> getAgents(int numAgents) {
		Set<AbstractAutonomousAgent> agents = new HashSet<>();
		for (int i = 0; i < numAgents; i++) {
			agents.add(FACTORY.createCustomDefaultAgent(
					FACTORY.getDefaultSensors(), FACTORY.getDefaultActuators(),
					new TestAgentMind()));
		}
		return agents;
	}

}
