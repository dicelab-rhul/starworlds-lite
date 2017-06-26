package uk.ac.rhul.cs.dice.starworlds.environment.concrete;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.Appearance;
import uk.ac.rhul.cs.dice.starworlds.appearances.EnvironmentAppearance;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.AbstractAmbient;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.Ambient;
import uk.ac.rhul.cs.dice.starworlds.environment.interaction.Event;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.AbstractConnectedEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.Environment;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.Universe;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractConnectedPhysics;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.Physics;

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
	 * Constructor. This constructor assumes that all
	 * {@link AbstractConnectedEnvironment}s will be local.
	 * 
	 * @param subenvironments
	 *            : the sub-{@link Environment}s of this {@link Environment}
	 * @param neighbouringenvironments
	 *            : the neighbouring-{@link Environment}s of this
	 *            {@link Environment}
	 * @param ambient
	 *            : a {@link Ambient} instance
	 * @param physics
	 *            : the {@link Physics} of the {@link Environment}
	 * @param appearance
	 *            : the {@link Appearance} of the {@link Environment}
	 * @param possibleActions
	 *            : the {@link Collection} of {@link Action}s that are possible
	 *            in this {@link Environment}
	 * @param bounded
	 *            : a {@link Boolean} value indicating whether the
	 *            {@link Environment} is bounded or not
	 */
	public DefaultEnvironment(
			Collection<AbstractConnectedEnvironment> subenvironments,
			Collection<AbstractConnectedEnvironment> neighbouringenvironments,
			AbstractAmbient ambient,
			AbstractConnectedPhysics physics,
			EnvironmentAppearance appearance,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions,
			Boolean bounded) {
		super(subenvironments, neighbouringenvironments, ambient, physics,
				appearance, possibleActions, bounded);
	}

	/**
	 * Constructor. This constructor assumes that all
	 * {@link AbstractConnectedEnvironment}s will be remote. All remote
	 * {@link Environment}s should connect via the given port.
	 * 
	 * @param port
	 *            : the port that any remote {@link Environment} will try to
	 *            make connections to
	 * @param ambient
	 *            : a {@link Ambient} instance
	 * @param physics
	 *            : the {@link Physics} of the {@link Environment}
	 * 
	 * @param appearance
	 *            : the {@link Appearance} of the {@link Environment}
	 * @param possibleActions
	 *            : a {@link Collection} of {@link Action}s that are possible in
	 *            this {@link Environment}
	 * @param bounded
	 *            : a {@link Boolean} value indicating whether the
	 *            {@link Environment} is bounded or not
	 */
	public DefaultEnvironment(
			Collection<AbstractConnectedEnvironment> subenvironments,
			Collection<AbstractConnectedEnvironment> neighbouringenvironments,
			Integer port,
			AbstractAmbient ambient,
			AbstractConnectedPhysics physics,
			EnvironmentAppearance appearance,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions,
			Boolean bounded) {
		super(subenvironments, neighbouringenvironments, port, ambient,
				physics, appearance, possibleActions, bounded);
	}

	/**
	 * Constructor. This Constructor allows local and remote {@link Environment}
	 * s to connect to this one. Remote {@link Environment}s should connect via
	 * the give port.
	 *
	 * @param subenvironments
	 *            : the sub-{@link Environment}s of this {@link Environment}
	 * @param neighbouringenvironments
	 *            : the neighbouring-{@link Environment}s of this
	 *            {@link Environment}
	 * @param port
	 *            : the port that any remote {@link Environment} will try to
	 *            make connections to
	 * @param ambient
	 *            : a {@link Ambient} instance
	 * @param physics
	 *            : the {@link Physics} of the environment
	 * 
	 * @param appearance
	 *            : the {@link Appearance} of the {@link Environment}
	 * @param possibleActions
	 *            : a {@link Collection} of {@link Action}s that are possible in
	 *            this {@link Environment}
	 * @param bounded
	 *            : a {@link Boolean} value indicating whether the
	 *            {@link Environment} is bounded or not
	 */
	public DefaultEnvironment(
			Integer port,
			AbstractAmbient ambient,
			AbstractConnectedPhysics physics,
			EnvironmentAppearance appearance,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions,
			Boolean bounded) {
		super(port, ambient, physics, appearance, possibleActions, bounded);
	}

	@Override
	public Boolean isSimple() {
		return false;
	}

	@Override
	public void handleCustomMessage(EnvironmentAppearance appearance,
			Event<?> message) {
	}
}