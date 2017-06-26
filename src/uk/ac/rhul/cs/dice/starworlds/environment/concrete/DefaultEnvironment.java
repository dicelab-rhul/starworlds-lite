package uk.ac.rhul.cs.dice.starworlds.environment.concrete;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.EnvironmentAppearance;
import uk.ac.rhul.cs.dice.starworlds.environment.base.AbstractAmbient;
import uk.ac.rhul.cs.dice.starworlds.environment.base.AbstractConnectedEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.Ambient;
import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.Environment;
import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.Message;
import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.Universe;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractConnectedPhysics;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.Physics;
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
public class DefaultEnvironment extends AbstractConnectedEnvironment {

	/**
	 * Constructor.
	 * 
	 * @param subenvironments
	 *            : a {@link Collection} of {@link Environment}s that are the
	 *            sub {@link Environment}s of this {@link Environment}.
	 * @param subenvironments
	 *            : a {@link Collection} of {@link Environment}s that are the
	 *            neighbouring {@link Environment}s of this {@link Environment}.
	 * @param state
	 *            : an {@link AbstractAmbient} instance.
	 * @param physics
	 *            : the {@link AbstractConnectedPhysics} of the environment.
	 * @param bounded
	 *            : a {@link Boolean} value indicating whether the environment
	 *            is bounded or not.
	 * @param appearance
	 *            : the {@link EnvironmentAppearance}
	 */
	public DefaultEnvironment(
			Collection<AbstractConnectedEnvironment> subenvironments,
			Collection<AbstractConnectedEnvironment> neighbourenvironments,
			AbstractAmbient state,
			AbstractConnectedPhysics physics,
			Boolean bounded,
			EnvironmentAppearance appearance,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions) {
		super(subenvironments, neighbourenvironments, state, physics, bounded,
				appearance, possibleActions);
	}

	/**
	 * Constructor. This constructor assumes that all
	 * {@link AbstractConnectedEnvironment}s will be remote. All remote
	 * {@link Environment}s should connect via the given port.
	 * 
	 * @param port
	 *            : the port that any remote {@link Environment} will try to
	 *            make connections to
	 * @param state
	 *            : a {@link Ambient} instance.
	 * @param physics
	 *            : the {@link Physics} of the environment.
	 * @param bounded
	 *            : a {@link Boolean} value indicating whether the environment
	 *            is bounded or not.
	 * @param possibleActions
	 *            : a {@link Collection} of {@link Action}s that are possible in
	 *            this {@link Environment}
	 */
	public DefaultEnvironment(
			Integer port,
			AbstractAmbient state,
			AbstractConnectedPhysics physics,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions) {
		super(port, state, physics, true, new EnvironmentAppearance(IDFactory
				.getInstance().getNewID(), false, false), possibleActions);
	}

	@Override
	public Boolean isSimple() {
		return false;
	}

	@Override
	public void handleCustomMessage(EnvironmentAppearance appearance,
			Message<?> message) {
	}
}