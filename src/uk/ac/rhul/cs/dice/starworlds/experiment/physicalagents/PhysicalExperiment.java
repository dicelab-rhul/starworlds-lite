package uk.ac.rhul.cs.dice.starworlds.experiment.physicalagents;

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
import uk.ac.rhul.cs.dice.starworlds.environment.concrete.DefaultEnvironment;
import uk.ac.rhul.cs.dice.starworlds.initialisation.AgentFactory;

public class PhysicalExperiment {

	private final static int NUMAGENTS = 2;

	public static void main(String[] args) {
		Set<Class<? extends AbstractEnvironmentalAction>> possibleActions = new HashSet<>();
		possibleActions.add(CommunicationAction.class);
		possibleActions.add(SensingAction.class);
		possibleActions.add(MoveAction.class);

		PhysicalPhysics physics = new PhysicalPhysics(possibleActions,
				getDefaultAgents(NUMAGENTS), null, null);
		new DefaultEnvironment(new PhysicalState(physics, 10), physics, null);
		physics.start(true);
	}

	public static Set<AbstractAgent> getDefaultAgents(int num) {
		Set<AbstractAgent> agents = new HashSet<>();
		for (int i = 0; i < num; i++) {

			List<Sensor> sensors = new ArrayList<>();
			sensors.add(new ListeningSensor());
			sensors.add(new BadSeeingSensor());
			AbstractAgent a = AgentFactory.getInstance()
					.createCustomDefaultAgent(null, sensors,
							AgentFactory.getInstance().getDefaultActuators(),
							new RandomPhysicalAgentMind());
			agents.add(a);
		}
		return agents;
	}

}
