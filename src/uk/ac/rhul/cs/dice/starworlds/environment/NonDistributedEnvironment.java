package uk.ac.rhul.cs.dice.starworlds.environment;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.Appearance;
import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.AbstractSensor;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.Physics;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.Subscriber;
import uk.ac.rhul.cs.dice.starworlds.perception.AbstractPerception;

public abstract class NonDistributedEnvironment extends
		AbstractEnvironment<AbstractSensor> {

	public NonDistributedEnvironment(
			State state,
			Physics physics,
			Boolean bounded,
			Appearance appearance,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions) {
		super(new Subscriber(), state, physics, bounded, appearance,
				possibleActions);
		this.physics.getAgents()
				.forEach(
						(AbstractAgent agent) -> {
							agent.setEnvironment(this);
							this.subscribe(
									agent,
									agent.getSensors().toArray(
											new AbstractSensor[] {}));
						});
		this.physics.getActiveBodies()
				.forEach(
						(ActiveBody body) -> {
							body.setEnvironment(this);
							this.subscribe(
									body,
									body.getSensors().toArray(
											new AbstractSensor[] {}));
						});
	}

	@Override
	public synchronized void subscribe(ActiveBody body, AbstractSensor[] sensors) {
		super.subscribe(body, sensors);
	}

	@Override
	public boolean isDistributed() {
		return false;
	}

	@Override
	protected void notifySensor(AbstractSensor sensor,
			AbstractPerception<?> perception) {
		sensor.notify(perception);
	}
}
