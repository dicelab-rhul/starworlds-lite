package uk.ac.rhul.cs.dice.starworlds.environment.physics.time;

import java.net.SocketAddress;

import uk.ac.rhul.cs.dice.starworlds.environment.base.AbstractEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.base.ConnectedEnvironmentManager;

public class RemoteSynchroniser implements Synchroniser {

	private ConnectedEnvironmentManager environmentManager;
	private SocketAddress remoteEnvironment;

	public RemoteSynchroniser(AbstractEnvironment localEnvironment,
			SocketAddress remoteEnvironment) {
		this.environmentManager = localEnvironment
				.getConnectedEnvironmentManager();
		this.remoteEnvironment = remoteEnvironment;
	}

	@Override
	public void runagents() {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeactions() {
		// TODO Auto-generated method stub

	}

}
