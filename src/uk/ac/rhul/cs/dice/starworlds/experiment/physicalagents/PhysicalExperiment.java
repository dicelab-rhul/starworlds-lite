package uk.ac.rhul.cs.dice.starworlds.experiment.physicalagents;

import java.util.ArrayList;
import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.CommunicationAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SensingAction;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.Mind;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.concrete.ListeningSensor;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.concrete.PhysicalActuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.concrete.SpeechActuator;
import uk.ac.rhul.cs.dice.starworlds.environment.concrete.DefaultWorld;
import uk.ac.rhul.cs.dice.starworlds.initialisation.AgentFactory;
import uk.ac.rhul.cs.dice.starworlds.initialisation.WorldDeployer;

public class PhysicalExperiment {

	private final static Collection<Class<? extends AbstractEnvironmentalAction>> POSSIBLEACTIONS = new ArrayList<>();
	private final static Collection<Class<?>> SENSORS = new ArrayList<>();
	private final static Collection<Class<?>> ACTUATORS = new ArrayList<>();
	private final static Class<? extends Mind> MIND = RandomPhysicalAgentMind.class;

	static {
		SENSORS.add(BadSeeingSensor.class);
		SENSORS.add(ListeningSensor.class);
		ACTUATORS.add(SpeechActuator.class);
		ACTUATORS.add(PhysicalActuator.class);
		POSSIBLEACTIONS.add(MoveAction.class);
		POSSIBLEACTIONS.add(CommunicationAction.class);
		POSSIBLEACTIONS.add(SensingAction.class);
	}

	public static void main(String[] args) throws Exception {
		WorldDeployer.deployAndRun(createWorld(2));
	}

	public static DefaultWorld createWorld(int numagents) {
		return new DefaultWorld(new PhysicalUniverse(new PhysicalAmbient(
				AgentFactory.getInstance().createDefaultAgents(numagents, MIND,
						SENSORS, ACTUATORS), null, null),
				new PhysicalPhysics(), POSSIBLEACTIONS));
	}
}
