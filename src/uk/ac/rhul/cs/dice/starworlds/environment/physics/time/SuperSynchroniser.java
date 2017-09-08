package uk.ac.rhul.cs.dice.starworlds.environment.physics.time;

import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.AbstractConnectedEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.Simulator;

public class SuperSynchroniser extends LocalSynchroniser implements Simulator {

	// default
	protected long framelength = 1000;

	public SuperSynchroniser(AbstractConnectedEnvironment environment) {
		super(environment);
	}

	public void setFrameLength(long framegap) {
		this.framelength = framegap;
	}

	/**
	 * Starts an the system simulation. The order of execution is as follows:
	 * {@link SuperSynchroniser#runActors() runagents()}, </br>
	 * {@link SuperSynchroniser#propagateActions() propagateActions()}, </br>
	 * {@link SuperSynchroniser#executeActions() executeactions()},</br>
	 * {@link SuperSynchroniser#cycleAddition() cycleAddition()}.</br>
	 */
	@Override
	public void simulate() {
		System.out.println(this + " SIMULATING...");
		while (true) {
			System.out.println("******************* CYCLE *******************");
			this.runActors();
			this.propagateActions();
			this.executeActions();
			this.cycleAddition();
			try {
				Thread.sleep(framelength);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
