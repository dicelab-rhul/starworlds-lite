package uk.ac.rhul.cs.dice.starworlds.experiment.communicatingagents;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.CommunicationAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SensingAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.EnvironmentAppearance;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.starworlds.environment.concrete.DefaultAmbient;
import uk.ac.rhul.cs.dice.starworlds.environment.concrete.DefaultConnectedPhysics;
import uk.ac.rhul.cs.dice.starworlds.environment.concrete.DefaultEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.concrete.DefaultUniverse;
import uk.ac.rhul.cs.dice.starworlds.environment.concrete.DefaultWorld;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.AbstractConnectedEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.Environment;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.Universe;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.WorldNode;
import uk.ac.rhul.cs.dice.starworlds.initialisation.AgentFactory;
import uk.ac.rhul.cs.dice.starworlds.initialisation.ConnectionInitialisationVisitor;
import uk.ac.rhul.cs.dice.starworlds.initialisation.IDFactory;
import uk.ac.rhul.cs.dice.starworlds.initialisation.InfoPrintVisitor;
import uk.ac.rhul.cs.dice.starworlds.initialisation.PostInitialisationVisitor;
import uk.ac.rhul.cs.dice.starworlds.initialisation.WorldDeployer;
import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.tree.GraphTreeNode;
import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.tree.GraphTree;

class ExperimentCommunication {

	private static AgentFactory FACTORY = AgentFactory.getInstance();
	// The set of actions possible in the Environments
	private static Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions = new HashSet<>();
	static {
		possibleActions.add(SensingAction.class);
		possibleActions.add(CommunicationAction.class);
	}

	public static void main(String[] args) throws Exception {
		// calling this method will start the simulation
		WorldDeployer.deployAndRun(createWorld(2));
	}

	/**
	 * Creates a chain of sub-{@link Environment}s and builds a
	 * {@link DefaultWorld World} from them. The simplest case is when the num
	 * argument is 1. This will create a single {@link DefaultUniverse}.
	 * 
	 * @param num
	 *            how many {@link Environment}s
	 * @return the {@link DefaultWorld World}
	 */
	public static DefaultWorld createWorld(int num) {
		num = (num > 1) ? num : 1;
		// create the universe
		DefaultWorld world = new DefaultWorld(new DefaultUniverse(
				new DefaultAmbient(getAgents(1), null, null),
				new DefaultConnectedPhysics(), new EnvironmentAppearance(
						IDFactory.getInstance().getNewID(), false, false),
				possibleActions));
		// create some sub environments
		WorldNode current = world.getRoot();
		for (int i = 1; i < num; i++) {
			WorldNode node = new WorldNode(new DefaultEnvironment(
					new DefaultAmbient(getAgents(1), null, null),
					new DefaultConnectedPhysics(), new EnvironmentAppearance(
							IDFactory.getInstance().getNewID(), false, false),
					possibleActions, true));
			current.addChild(node);
			current = node;
		}
		return world;
	}

	// creates some default communicating agents
	private static Set<AbstractAgent> getAgents(int numAgents) {
		Set<AbstractAgent> agents = new HashSet<>();
		for (int i = 0; i < numAgents; i++) {
			agents.add(FACTORY.createCustomDefaultAgent(
					FACTORY.getDefaultSensors(), FACTORY.getDefaultActuators(),
					new RandomCommunicatingAgentMind()));
		}
		return agents;
	}
}
