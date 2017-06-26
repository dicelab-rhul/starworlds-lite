package uk.ac.rhul.cs.dice.starworlds.environment.physics.time;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.entities.Agent;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.Mind;
import uk.ac.rhul.cs.dice.starworlds.environment.interaction.inet.INetEnvironmentConnection;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.AbstractConnectedEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.Environment;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractConnectedPhysics;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.time.RemoteSynchroniser.SyncPoint;

public class LocalSynchroniser implements Synchroniser {

	private Collection<RemoteSynchroniser> remoteSynchronisers;
	private Collection<Synchroniser> synchronisers;
	private AbstractConnectedPhysics physics;
	private AbstractConnectedEnvironment environment;

	public LocalSynchroniser(AbstractConnectedEnvironment environment,
			Collection<AbstractConnectedEnvironment> subenvironments,
			Collection<AbstractConnectedEnvironment> neighbouringenvironments) {
		this.environment = environment;
		this.physics = (AbstractConnectedPhysics) environment.getPhysics();
		this.synchronisers = new ArrayList<>();
		this.remoteSynchronisers = new ArrayList<>();
		// get all local synchronisers
		updateSynchronisers(subenvironments, neighbouringenvironments);
		System.out.println(this + " SUBSYNCS: " + synchronisers);
	}

	public LocalSynchroniser(AbstractConnectedEnvironment environment) {
		this.environment = environment;
		this.physics = (AbstractConnectedPhysics) environment.getPhysics();
		this.synchronisers = new ArrayList<>();
		this.remoteSynchronisers = new ArrayList<>();
		// get all local synchronisers
		updateSynchronisers(null, null);
	}

	public RemoteSynchroniser addRemoteSynchroniser(
			INetEnvironmentConnection remoteEnvironment) {
		RemoteSynchroniser sync = new RemoteSynchroniser(remoteEnvironment);
		remoteSynchronisers.add(sync);
		return sync;
	}

	public void updateSynchronisers(
			Collection<AbstractConnectedEnvironment> subenvironments,
			Collection<AbstractConnectedEnvironment> neighbouringenvironments) {
		// TODO check that the synchroniser has not been added already
		// add local synchronisers
		if (subenvironments != null) {
			subenvironments.forEach((AbstractConnectedEnvironment e) -> {
				// TODO if the connection starts closed?
					synchronisers.add(((AbstractConnectedPhysics) e
							.getPhysics()).getSynchroniser());
				});
		}
	}

	/**
	 * Calls the {@link Agent#run()} method for all {@link Agent}s in the
	 * system. Each {@link Agent} performs its {@link Mind#cycle(Object...)
	 * cycle(Object...)} procedure here. Calls to sub {@link Environment}
	 * {@link LocalSynchroniser}s are made recursively down the hierarchy, with
	 * the lower {@link LocalSynchroniser}s executing first.
	 */
	@Override
	public void runagents() {
		// TODO multithreaded
		System.out.println(this + " RUNNINGAGENTS...");
		for (Synchroniser s : synchronisers) {
			s.runagents();
		}

		physics.runAgents();
		done(SyncPoint.RUNAGENTS);
		wait(syncWithRemoteEnvironments(SyncPoint.RUNAGENTS));
	}

	/**
	 * Propagates {@link Action}s between {@link Environment}s local or remote.
	 * Calls to sub {@link Environment} {@link LocalSynchroniser}s are made
	 * recursively down the hierarchy, with the lower {@link LocalSynchroniser}s
	 * executing first.
	 */
	@Override
	public void propagateActions() {
		// TODO multithreaded
		System.out.println(this + " PROPAGATING ACTIONS...");

		for (Synchroniser s : synchronisers) {
			s.propagateActions();
		}
		physics.propagateActions();
		done(SyncPoint.PROPAGATEACTIONS);
		wait(syncWithRemoteEnvironments(SyncPoint.PROPAGATEACTIONS));
	}

	/**
	 * Executes {@link Action}s that have been received by all
	 * {@link Environment} after actions have been propagated. Calls to sub
	 * {@link Environment} {@link LocalSynchroniser}s are made recursively down
	 * the hierarchy, with the lower {@link LocalSynchroniser}s executing first.
	 */
	@Override
	public void executeactions() {
		// TODO multithreaded
		System.out.println(this + " EXECUTING ACTIONS...");
		for (Synchroniser s : synchronisers) {
			s.executeactions();
		}
		physics.executeActions();
		done(SyncPoint.EXECUTEACTIONS);
		wait(syncWithRemoteEnvironments(SyncPoint.EXECUTEACTIONS));
	}

	protected void done(SyncPoint reached) {
		remoteSynchronisers.forEach((remote) -> remote.done(reached));
	}

	protected List<Thread> syncWithRemoteEnvironments(SyncPoint point) {
		List<Thread> ts = new ArrayList<>();
		remoteSynchronisers.forEach((remote) -> {
			remote.setCurrent(point);
			Thread t = new Thread(remote);
			ts.add(t);
			t.start();
		});
		return ts;
	}

	protected void wait(List<Thread> threads) {
		for (Thread t : threads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " : " + physics.getId();
	}

}
