package uk.ac.rhul.cs.dice.starworlds.environment;

import java.io.Serializable;
import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.Appearance;
import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Sensor;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.Physics;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.SubscriberInet;
import uk.ac.rhul.cs.dice.starworlds.perception.AbstractPerception;
import uk.ac.rhul.cs.dice.starworlds.utils.inet.INetComponent;

public abstract class DistributedEnvironment extends
		AbstractEnvironment<String> {

	private INetComponent server;

	public DistributedEnvironment(
			State state,
			Physics physics,
			Boolean bounded,
			Appearance appearance,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions,
			int port) {
		super(new SubscriberInet(), state, physics, bounded, appearance,
				possibleActions);
		this.server = new INetComponent(port);
	}

	@Override
	protected SubscriberInet getSubscriber() {
		return (SubscriberInet) super.subscriber;
	}

	@Override
	public void notify(AbstractEnvironmentalAction action, ActiveBody body,
			Collection<AbstractPerception<?>> perceptions, State context) {
		// TODO Auto-generated method stub
		System.out.println("ENV NOTIFY");
		for (AbstractPerception<?> p : perceptions) {
			if (Serializable.class.isAssignableFrom(p.getClass())) {
			} else {
				System.err
						.println("The perception: "
								+ p
								+ "is not serializable. It cannot be sent over network. "
								+ System.lineSeparator()
								+ "If a distributed environment is being used,"
								+ System.lineSeparator()
								+ "ensure that any perception that is to be sent over network implements the Serializable interface");
			}
		}
	}

	@Override
	protected boolean checkPerceivable(String sensor,
			AbstractPerception<?> perception, State context) {
		return true; // TODO, we dont have an instance of the sensor this may
						// have to be done remotely
	}

	@Override
	protected void notifySensor(String sensor, AbstractPerception<?> perception) {
		// TODO Auto-generated method stub

	}

	/**
	 * Notifies all agents (one of their {@link Sensor}s) with the null
	 * perception so that they may continue. i.e. they realise that this is the
	 * first cycle.
	 */
	protected void notifyAllNull() {
		//
	}
}
