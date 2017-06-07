package uk.ac.rhul.cs.dice.starworlds.experiment;

import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.PassiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractPhysics;

public class ExperimentPhysics extends AbstractPhysics {

	private StringBuilder info;

	public ExperimentPhysics(Set<AbstractAgent> agents,
			Set<ActiveBody> activeBodies, Set<PassiveBody> passiveBodies,
			String id) {
		super(agents, activeBodies, passiveBodies);
		this.setId(id);
		System.out.println("PHYSICS: " + id + " " + this.getAgents());
	}

	protected void optional() {
	}

	private void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private double getTime(Long start) {
		return (double) (System.nanoTime() - start) / 1000000000.0;
	}
}
