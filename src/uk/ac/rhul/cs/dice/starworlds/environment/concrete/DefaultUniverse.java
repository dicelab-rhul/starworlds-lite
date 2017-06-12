package uk.ac.rhul.cs.dice.starworlds.environment.concrete;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.EnvironmentAppearance;
import uk.ac.rhul.cs.dice.starworlds.environment.base.AbstractConnectedEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.base.AbstractState;
import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.Universe;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractConnectedPhysics;
import uk.ac.rhul.cs.dice.starworlds.initialisation.IDFactory;

/**
 * An extension of {@link ComplexEnvironment} that is a {@link Universe}. </br>
 * </br> Known direct subclasses: none.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public class DefaultUniverse extends ComplexEnvironment implements Universe {

	/**
	 * TODO
	 * 
	 * @param state
	 * @param physics
	 * @param appearance
	 * @param possibleActions
	 * @param subenvironments
	 */
	public DefaultUniverse(
			Collection<AbstractConnectedEnvironment> subenvironments,
			AbstractState state,
			AbstractConnectedPhysics physics,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions) {
		super(subenvironments, state, physics, false,
				new EnvironmentAppearance(IDFactory.getInstance().getNewID(),
						false, false), possibleActions);
	}

	/**
	 * TODO
	 * 
	 * @param state
	 * @param physics
	 * @param appearance
	 * @param possibleActions
	 * @param subenvironments
	 */
	public DefaultUniverse(
			Collection<AbstractConnectedEnvironment> subenvironments,
			AbstractState state,
			AbstractConnectedPhysics physics,
			EnvironmentAppearance appearance,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions) {
		super(subenvironments, state, physics, false, appearance,
				possibleActions);
	}

	@Override
	protected void initialActionSubscribe() {
		DefaultMessage<Collection<Class<? extends AbstractEnvironmentalAction>>> sub = new DefaultMessage<>(
				this.getAppearance(), SUBSCRIBE, getInitialActionsToSubscribe());
		// this.connectedEnvironmentManager.sendToAllNeighbouringEnvironments(sub);
		// //?
		this.connectedEnvironmentManager.sendToAllSubEnvironments(sub);
	}

	@Override
	public void simulate() {
		physics.simulate();
	}

	@Override
	public boolean isDistributed() {
		return false;
	}
}