package uk.ac.rhul.cs.dice.starworlds.environment.physics.time;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.environment.base.AbstractEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.base.ConnectedEnvironmentManager;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractPhysics;

public class LocalSynchroniser implements Synchroniser {

	private Collection<Synchroniser> subsynchronisers;
	private ConnectedEnvironmentManager environmentManager;
	private AbstractPhysics physics;
	private AbstractEnvironment environment;

	public LocalSynchroniser(AbstractEnvironment environment) {
		this.environment = environment;
		this.environmentManager = environment.getConnectedEnvironmentManager();
		this.physics = (AbstractPhysics) environment.getPhysics();
		this.subsynchronisers = new ArrayList<>();
		// get all local synchronisers
		updateSynchronisers();
		System.out.println("SUBSYNCS: " + subsynchronisers);
	}

	public void updateSynchronisers() {
		// TODO check that the synchroniser has not been added already
		// add local synchronisers
		environmentManager.getSubEnvironments().forEach(
				(AbstractEnvironment e) -> {
					subsynchronisers.add(e.getPhysics().getSynchronizer());
				});
		// create remote synchronisers
		environmentManager.getRemoteSubEnvironments().forEach(
				(SocketAddress a) -> {
					subsynchronisers
							.add(new RemoteSynchroniser(environment, a));
				});
	}

	@Override
	public void runagents() {
		// TODO multithreaded
		for (Synchroniser s : subsynchronisers) {
			s.runagents();
		}
		physics.executeActions();
	}

	@Override
	public void executeactions() {
		// TODO multithreaded
		for (Synchroniser s : subsynchronisers) {
			s.executeactions();
		}
		physics.executeActions();
	}

}
