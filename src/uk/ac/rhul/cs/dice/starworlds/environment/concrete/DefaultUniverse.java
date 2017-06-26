package uk.ac.rhul.cs.dice.starworlds.environment.concrete;

import java.io.Serializable;
import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.EnvironmentAppearance;
import uk.ac.rhul.cs.dice.starworlds.environment.base.AbstractAmbient;
import uk.ac.rhul.cs.dice.starworlds.environment.base.AbstractConnectedEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.Ambient;
import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.Environment;
import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.Universe;
import uk.ac.rhul.cs.dice.starworlds.environment.inet.INetDefaultMessage;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractConnectedPhysics;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.Physics;

/**
 * An extension of {@link DefaultEnvironment} that is a {@link Universe}. </br>
 * </br> Known direct subclasses: none.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public class DefaultUniverse extends DefaultEnvironment implements Universe {

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
	public DefaultUniverse(
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
	public DefaultUniverse(
			Integer port,
			AbstractAmbient state,
			AbstractConnectedPhysics physics,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions) {
		super(port, state, physics, possibleActions);
	}

	@Override
	protected void initialActionSubscribe() {
		INetDefaultMessage sub = new INetDefaultMessage(SUBSCRIBE,
				(Serializable) getInitialActionsToSubscribe());
		this.envconManager.sendToAllNeighbouringEnvironments(sub);
		this.envconManager.sendToAllSubEnvironments(sub);
	}

	@Override
	public void simulate() {
		physics.simulate();
	}
}