package uk.ac.rhul.cs.dice.starworlds.environment.concrete;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.EnvironmentAppearance;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.AbstractAmbient;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.AbstractEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.Environment;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.Universe;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractPhysics;

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
		super(ambient, physics, appearance, possibleActions, bounded);
	}

	public DefaultSimpleUniverse(
			AbstractAmbient ambient,
			AbstractPhysics physics,
			EnvironmentAppearance appearance,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions) {
		super(ambient, physics, appearance, possibleActions);
	}

	public DefaultSimpleUniverse(
			AbstractAmbient ambient,
			AbstractPhysics physics,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions) {
		super(ambient, physics, possibleActions);
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
