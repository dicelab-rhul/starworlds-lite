package uk.ac.rhul.cs.dice.starworlds.experiment.physicalagents;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.CommunicationAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SensingAction;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractAgentMind;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.concrete.ListeningSensor;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.concrete.PhysicalActuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.concrete.SpeechActuator;
import uk.ac.rhul.cs.dice.starworlds.entities.avatar.AbstractAvatarAgent;
import uk.ac.rhul.cs.dice.starworlds.entities.avatar.DefaultSelfishAvatarMind;
import uk.ac.rhul.cs.dice.starworlds.entities.avatar.DefaultSelflessAvatarMind;
import uk.ac.rhul.cs.dice.starworlds.environment.concrete.DefaultWorld;
import uk.ac.rhul.cs.dice.starworlds.initialisation.AgentFactory;
import uk.ac.rhul.cs.dice.starworlds.initialisation.WorldDeployer;

public class PhysicalExperiment {

	private final static Collection<Class<? extends AbstractEnvironmentalAction>> POSSIBLEACTIONS = new ArrayList<>();
	private final static Collection<Class<?>> SENSORS = new ArrayList<>();
	private final static Collection<Class<?>> ACTUATORS = new ArrayList<>();
	private final static Class<? extends AbstractAgentMind> MIND = RandomPhysicalAgentMind.class;
	private final static boolean selfishavatar = true;

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
		DefaultWorld world = createWorld(0);
		new PhysicalViewController(world.getUniverse());
		WorldDeployer.deployAndRun(world);
	}

	public static DefaultWorld createWorld(int numagents) {
		return new DefaultWorld(new PhysicalUniverse(new PhysicalAmbient(
				AgentFactory.getInstance().createDefaultAgents(numagents, MIND,
						SENSORS, ACTUATORS), null, null,
				(selfishavatar) ? getSelfishAvatar() : getSelflessAvatar()),
				new PhysicalPhysics(), POSSIBLEACTIONS));
	}

	public static Set<AbstractAvatarAgent<?>> getSelflessAvatar() {
		Set<AbstractAvatarAgent<?>> avatars = new HashSet<>();
		avatars.add(AgentFactory.getInstance().createDefaultCustomAvatar(
				SENSORS, ACTUATORS, DefaultSelflessAvatarMind.class));
		return avatars;
	}

	public static Set<AbstractAvatarAgent<?>> getSelfishAvatar() {
		Set<AbstractAvatarAgent<?>> avatars = new HashSet<>();
		avatars.add(AgentFactory.getInstance().createDefaultCustomAvatar(
				SENSORS, ACTUATORS, DefaultSelfishAvatarMind.class));
		return avatars;
	}
}
