package uk.ac.rhul.cs.dice.starworlds.environment.concrete;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.EnvironmentAppearance;
import uk.ac.rhul.cs.dice.starworlds.environment.base.AbstractConnectedEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.base.AbstractMessage;
import uk.ac.rhul.cs.dice.starworlds.environment.base.AbstractState;
import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.Environment;
import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.Universe;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractConnectedPhysics;
import uk.ac.rhul.cs.dice.starworlds.environment.subscriber.Subscriber;
import uk.ac.rhul.cs.dice.starworlds.initialisation.IDFactory;

/**
 * A concrete implementation of {@link Environment} that may contain a number of
 * sub {@link Environment}s. It should also have a super {@link Environment}
 * which may or may not be a {@link Universe}. All connected {@link Environment}
 * s are assumed to be local, all sub {@link Environment}s should be given on
 * instantiation of this class.
 * 
 * Known direct subclasses: {@link DefaultUniverse}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public class ComplexEnvironment extends AbstractConnectedEnvironment {

	/**
	 * Constructor.
	 * 
	 * @param subenvironments
	 *            : a {@link Collection} of {@link Environment}s that are the
	 *            sub {@link Environment}s of this {@link Environment}.
	 * @param state
	 *            : an {@link AbstractState} instance.
	 * @param physics
	 *            : the {@link AbstractConnectedPhysics} of the environment.
	 * @param bounded
	 *            : a {@link Boolean} value indicating whether the environment
	 *            is bounded or not.
	 * @param appearance
	 *            : the {@link EnvironmentAppearance}
	 */
	public ComplexEnvironment(
			Collection<AbstractConnectedEnvironment> subenvironments,
			AbstractState state,
			AbstractConnectedPhysics physics,
			Boolean bounded,
			EnvironmentAppearance appearance,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions) {
		super(subenvironments, null, new Subscriber(), state, physics, bounded,
				appearance, possibleActions);
	}

	/**
	 * Constructor.
	 * 
	 * @param subenvironments
	 *            : a {@link Collection} of {@link Environment}s that are the
	 *            sub {@link Environment}s of this {@link Environment}.
	 * @param state
	 *            : an {@link AbstractState} instance.
	 * @param physics
	 *            : the {@link AbstractConnectedPhysics} of the environment.
	 * @param bounded
	 *            : a {@link Boolean} value indicating whether the environment
	 *            is bounded or not.
	 * @param possibleActions
	 *            : the {@link AbstractEnvironmentalAction}s that are possible
	 *            in this {@link Environment}
	 */
	public ComplexEnvironment(
			Collection<AbstractConnectedEnvironment> subenvironments,
			AbstractState state,
			AbstractConnectedPhysics physics,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions) {
		super(subenvironments, null, new Subscriber(), state, physics, true,
				new EnvironmentAppearance(IDFactory.getInstance().getNewID(),
						false, false), possibleActions);
	}

	@Override
	public Boolean isSimple() {
		return false;
	}

	@Override
	public boolean isDistributed() {
		return false;
	}

	@Override
	public void handleCustomMessage(EnvironmentAppearance appearance,
			AbstractMessage<?> message) {
		// TODO Auto-generated method stub

	}
}