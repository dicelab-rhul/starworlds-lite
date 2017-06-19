package uk.ac.rhul.cs.dice.starworlds.environment.physics.time;

import uk.ac.rhul.cs.dice.starworlds.environment.base.EnvironmentConnectionManager;
import uk.ac.rhul.cs.dice.starworlds.environment.inet.INetEnvironmentConnection;
import uk.ac.rhul.cs.dice.starworlds.environment.inet.SynchronisationMessage;

public class RemoteSynchroniser implements Runnable {

	public static enum SyncPoint {
		RUNAGENTS, EXECUTEACTIONS, PROPAGATEACTIONS;
	}

	private EnvironmentConnectionManager environmentManager;
	private INetEnvironmentConnection remoteEnvironment;
	private SyncPoint current;
	private volatile SyncPoint received;

	public RemoteSynchroniser(EnvironmentConnectionManager environmentManager,
			INetEnvironmentConnection remoteEnvironment) {
		this.environmentManager = environmentManager;
		this.remoteEnvironment = remoteEnvironment;
		System.out.println("REMOTE SYNC");
	}

	@Override
	public void run() {
		System.out.println(this + " waiting...");
		while (waitCondition()) {
		}
		System.out.println(this + " ...continue");
	}

	public void done(SyncPoint reached) {
		System.out.println(this + " ...DONE");
		remoteEnvironment.send(new SynchronisationMessage(reached));
	}

	public void receiveSyncMessage(SyncPoint point) {
		System.out.println("SYNCMESSAGE FINISHED: " + point);
		received = point;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

	public SyncPoint getCurrent() {
		return current;
	}

	public void setCurrent(SyncPoint current) {
		this.current = current;
	}

	public boolean waitCondition() {
		return !current.equals(received);
	}
}
