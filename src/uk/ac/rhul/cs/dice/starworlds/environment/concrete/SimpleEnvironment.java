package uk.ac.rhul.cs.dice.starworlds.environment.concrete;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.EnvironmentAppearance;
import uk.ac.rhul.cs.dice.starworlds.environment.base.AbstractConnectedEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.base.AbstractState;
import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.Environment;
import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.Message;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractConnectedPhysics;
import uk.ac.rhul.cs.dice.starworlds.environment.subscriber.Subscriber;

/**
 * The one of the simplest concrete implementation of an {@link Environment}. It
 * contains no sub {@link Environment}s, has no neighbouring {@link Environment}
 * s. It should have a super {@link Environment}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public class SimpleEnvironment extends AbstractConnectedEnvironment {

	public SimpleEnvironment(
			AbstractState state,
			AbstractConnectedPhysics physics,
			EnvironmentAppearance appearance,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions) {
		super(null, null, new Subscriber(), state, physics, true, appearance,
				possibleActions);
	}

	public Boolean isSimple() {
		return true;
	}

	@Override
	public boolean isDistributed() {
		return false;
	}

	@Override
	public void handleCustomMessage(EnvironmentAppearance appearance,
			Message<?> message) {
		System.err.println("UNKNOWN MESSAGE FROM: " + appearance
				+ System.lineSeparator() + message);
	}
}