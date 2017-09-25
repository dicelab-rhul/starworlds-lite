package uk.ac.rhul.cs.dice.starworlds.test;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.CommunicationAction;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.concrete.ListeningSensor;
import uk.ac.rhul.cs.dice.starworlds.environment.subscription.AbstractSubscriptionHandler;
import uk.ac.rhul.cs.dice.starworlds.environment.subscription.SensorSubscriptionHandler;

public class TestSensorSubscriber {

	public static void main(String[] args) {
		AbstractSubscriptionHandler ss = new SensorSubscriptionHandler();
		ss.addPossibleAction(CommunicationAction.class);
		ss.addNewSensorType(ListeningSensor.class);
		System.out.println(ss);
	}

}
