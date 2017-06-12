package uk.ac.rhul.cs.dice.starworlds.experiment.communicatingagents;

import java.net.SocketAddress;
import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.Appearance;
import uk.ac.rhul.cs.dice.starworlds.environment.DistributedUniverse;
import uk.ac.rhul.cs.dice.starworlds.environment.base.AbstractEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.State;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.Physics;

public class DistributedCommunicationUniverse extends DistributedUniverse {

	public DistributedCommunicationUniverse(
			State state,
			Physics physics,
			Appearance appearance,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions,
			int port, AbstractEnvironment... environments) {
		super(state, physics, appearance, possibleActions, port, environments);
	}

	@Override
	public void receivedmessage(SocketAddress address, Object arg) {
		System.out.println(arg);
	}

}
