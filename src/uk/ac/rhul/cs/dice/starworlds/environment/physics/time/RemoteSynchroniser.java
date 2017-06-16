package uk.ac.rhul.cs.dice.starworlds.environment.physics.time;

import uk.ac.rhul.cs.dice.starworlds.appearances.EnvironmentAppearance;
import uk.ac.rhul.cs.dice.starworlds.environment.base.EnvironmentConnectionManager;

public class RemoteSynchroniser implements Synchroniser {

	private EnvironmentConnectionManager environmentManager;
	private EnvironmentAppearance remoteEnvironment;

	public RemoteSynchroniser(EnvironmentConnectionManager environmentManager,
			EnvironmentAppearance remoteEnvironment) {
		this.environmentManager = environmentManager;
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
