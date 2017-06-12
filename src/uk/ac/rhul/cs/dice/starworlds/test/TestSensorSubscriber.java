package uk.ac.rhul.cs.dice.starworlds.test;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.CommunicationAction;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.concrete.ListeningSensor;
import uk.ac.rhul.cs.dice.starworlds.environment.subscriber.AbstractSubscriber;
import uk.ac.rhul.cs.dice.starworlds.environment.subscriber.Subscriber;

public class TestSensorSubscriber {

	public static void main(String[] args) {
		AbstractSubscriber ss = new Subscriber();
		ss.addPossibleAction(CommunicationAction.class);
		ss.addNewSensorType(ListeningSensor.class);
		System.out.println(ss);
	}

}
