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
	 * {@link SuperSynchroniser#runagents() runagents()}, </br>
	 * {@link SuperSynchroniser#propagateActions() propagateActions()}, </br>
	 * {@link SuperSynchroniser#executeactions() executeactions()},</br>
	 * {@link SuperSynchroniser#cycleAddition() cycleAddition()}.</br>
	 */
	@Override
	public void simulate() {
		System.out.println(this + " SIMULATING...");
		while (true) {
			System.out.println("******************* CYCLE *******************");
			this.runagents();
			this.propagateActions();
			this.executeactions();
			this.cycleAddition();
			try {
				Thread.sleep(framelength);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
