package uk.ac.rhul.cs.dice.starworlds.environment.physics.time;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.environment.base.AbstractConnectedEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.Simulator;

public class SuperSynchroniser extends LocalSynchroniser implements Simulator {

	// default
	protected long framegap = 1000;

	public SuperSynchroniser(AbstractConnectedEnvironment environment,
			Collection<AbstractConnectedEnvironment> subenvironments,
			Collection<AbstractConnectedEnvironment> neighbouringenvironments) {
		super(environment, subenvironments, neighbouringenvironments);
	}

	public SuperSynchroniser(AbstractConnectedEnvironment environment) {
		super(environment);
	}

	public long getFramegap() {
		return framegap;
	}

	public void setFramegap(long framegap) {
		this.framegap = framegap;
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
				Thread.sleep(framegap);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * A method that should be overridden in a subclass of
	 * {@link SuperSynchroniser} if there are any additional things to be done
	 * at the end of a cycle. See {@link SuperSynchroniser#simulate()}.
	 */
	public void cycleAddition() {
	}
}
