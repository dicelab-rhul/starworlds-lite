package uk.ac.rhul.cs.dice.starworlds.environment.physics.time;

import java.net.SocketAddress;

import uk.ac.rhul.cs.dice.starworlds.environment.base.AbstractConnectedEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.base.EnvironmentConnectionManager;

public class RemoteSynchroniser implements Synchroniser {

	private EnvironmentConnectionManager environmentManager;
	private SocketAddress remoteEnvironment;

	public RemoteSynchroniser(AbstractConnectedEnvironment localEnvironment,
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

	@Override
	public void propagateActions() {
		// TODO Auto-generated method stub
		
	}

}
