package uk.ac.rhul.cs.dice.starworlds.environment.physics.time;

import uk.ac.rhul.cs.dice.starworlds.environment.interaction.inet.INetEnvironmentConnection;
import uk.ac.rhul.cs.dice.starworlds.environment.interaction.inet.SynchronisationMessage;

public class RemoteSynchroniser implements Runnable {

	public static enum SyncPoint {
		RUNAGENTS, EXECUTEACTIONS, PROPAGATEACTIONS;
	}

	private INetEnvironmentConnection remoteEnvironment;
	private SyncPoint current;
	private volatile SyncPoint received;

	public RemoteSynchroniser(INetEnvironmentConnection remoteEnvironment) {
		this.remoteEnvironment = remoteEnvironment;
	}

	@Override
	public void run() {
		if (waitCondition()) {
			System.out.println(remoteEnvironment.getAppearance().getId()
					+ " Current: " + current + " waiting for: "
					+ remoteEnvironment.getRemoteAppearance().getId());
			while (waitCondition()) {
			}
			System.out.println(remoteEnvironment.getAppearance().getId()
					+ " ...continue");
		}
	}

	public void done(SyncPoint reached) {
		System.out.println(this + " ...done:" + reached);
		remoteEnvironment.send(new SynchronisationMessage(reached));
	}

	public void receiveSyncMessage(SyncPoint point) {
		System.out.println("Received sync message: " + point);
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
