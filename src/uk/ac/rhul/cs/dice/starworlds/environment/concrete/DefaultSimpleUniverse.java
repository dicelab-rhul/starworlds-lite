package uk.ac.rhul.cs.dice.starworlds.environment.concrete;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.EnvironmentAppearance;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.AbstractAmbient;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.AbstractEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.Environment;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.Universe;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractPhysics;
import uk.ac.rhul.cs.dice.starworlds.environment.subscription.SensorSubscriptionHandler;
import uk.ac.rhul.cs.dice.starworlds.initialisation.ReflectiveMethodStore;

/**
 * A default {@link Universe} that cannot make any connections to other
 * {@link Environment}s. It has no neighbouring or sub {@link Environment}s.
 * 
 * @author Ben Wilkins
 *
 */
public class DefaultSimpleUniverse extends AbstractEnvironment implements
		Universe {

	public DefaultSimpleUniverse(
			AbstractAmbient ambient,
			AbstractPhysics physics,
			EnvironmentAppearance appearance,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions,
			Boolean bounded) {
		super(ambient, physics, new SensorSubscriptionHandler(), appearance,
				possibleActions, bounded);
		validatePhysics();
	}

	public DefaultSimpleUniverse(
			AbstractAmbient ambient,
			AbstractPhysics physics,
			EnvironmentAppearance appearance,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions) {
		super(ambient, physics, appearance, possibleActions);
		validatePhysics();
	}

	public DefaultSimpleUniverse(
			AbstractAmbient ambient,
			AbstractPhysics physics,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions) {
		super(ambient, physics, possibleActions);
		validatePhysics();
	}

	private void validatePhysics() {
		ReflectiveMethodStore.validateReflectiveActions(
				this.physics.getClass(), this.possibleActions);
	}

	@Override
	public void postInitialisation() {
	}

	@Override
	public Boolean isSimple() {
		return true;
	}

	@Override
	public void simulate() {
		physics.simulate();
	}

	@Override
	public void run() {
		this.simulate();
	}

}
