package uk.ac.rhul.cs.dice.starworlds.experiments.synchronisationexperiment;

import java.util.ArrayList;
import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.environment.ambient.Ambient;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractConnectedPhysics;
import uk.ac.rhul.cs.dice.starworlds.perception.AbstractPerception;
import uk.ac.rhul.cs.dice.starworlds.perception.DefaultPerception;

public class TestPhysics extends AbstractConnectedPhysics {

	@Override
	public void cycleAddition() {
	}

	@Override
	public void executeActions() {
		try {
			Long sleep = (long) (Math.random() * 5000);
			System.out.println(this.getId() + "SLEEP: " + sleep);
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		super.executeActions();
	}

	@Override
	public void propagateActions() {
		try {
			Long sleep = (long) (Math.random() * 5000);
			System.out.println(this.getId() + "SLEEP: " + sleep);
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		super.propagateActions();
	}

	@Override
	public void runAgents() {
		try {
			Long sleep = (long) (Math.random() * 5000);
			System.out.println(this.getId() + "SLEEP: " + sleep);
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		super.runAgents();
	}

	public boolean perform(TestAction action, Ambient context) {
		return true;
	}

	public boolean isPossible(TestAction action, Ambient context) {
		return true;
	}

	public boolean verify(TestAction action, Ambient context) {
		return true;
	}

	public Collection<AbstractPerception<?>> getAgentPerceptions(
			TestAction action, Ambient context) {
		Collection<AbstractPerception<?>> percepts = new ArrayList<>();
		percepts.add(new DefaultPerception<String>("TESTPERCEPTION!"));
		return percepts;
	}

	public Collection<AbstractPerception<?>> getOtherPerceptions(
			TestAction action, Ambient context) {
		return null;
	}

}
