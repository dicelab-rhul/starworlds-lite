package uk.ac.rhul.cs.dice.starworlds.test;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.CommunicationAction;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.concrete.ListeningSensor;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.SensorSubscriber;

public class TestSensorSubscriber {

	public static void main(String[] args) {
		SensorSubscriber ss = new SensorSubscriber();
		ss.addPossibleAction(CommunicationAction.class);
		ss.addNewSensorType(ListeningSensor.class);
		System.out.println(ss);
	}

}
