package uk.ac.rhul.cs.dice.starworlds.experiment;

import java.util.Collection;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.PassiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractPhysics;

public class ExperimentPhysics extends AbstractPhysics {

	private StringBuilder info;

	public ExperimentPhysics(
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions,
			Set<AbstractAgent> agents, Set<ActiveBody> activeBodies,
			Set<PassiveBody> passiveBodies) {
		super(possibleActions, agents, activeBodies, passiveBodies);
	}

	@Override
	public void start(boolean serial) {
		int count = 0;
		Long totaltime = 0l;
		super.setTimeState(serial);
		while (true) {
			count++;
			info = new StringBuilder("---------------------"
					+ System.lineSeparator());
			System.out.println(info.toString());
			Long time = System.nanoTime();
			super.timestate.simulate();
			optional();
			info.append(System.lineSeparator() + "TOTAL TIME: "
					+ String.valueOf(getTime(time)));
			System.out.println(info.toString());
			sleep(1000);
		}
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
