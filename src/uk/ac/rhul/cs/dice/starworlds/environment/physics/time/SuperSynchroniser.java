package uk.ac.rhul.cs.dice.starworlds.environment.physics.time;

import uk.ac.rhul.cs.dice.starworlds.environment.Simulator;
import uk.ac.rhul.cs.dice.starworlds.environment.base.AbstractEnvironment;

public class SuperSynchroniser extends LocalSynchroniser implements Simulator {

	// default
	protected long framegap = 1000;

	public SuperSynchroniser(AbstractEnvironment environment) {
		super(environment);
	}

	public long getFramegap() {
		return framegap;
	}

	public void setFramegap(long framegap) {
		this.framegap = framegap;
	}

	@Override
	public void simulate() {
		while (true) {
			this.runagents();
			this.executeactions();
			try {
				Thread.sleep(framegap);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
