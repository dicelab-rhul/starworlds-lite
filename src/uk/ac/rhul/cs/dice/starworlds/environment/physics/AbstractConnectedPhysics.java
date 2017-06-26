package uk.ac.rhul.cs.dice.starworlds.environment.physics;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.entities.Agent;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.AbstractConnectedEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.Environment;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.Simulator;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.time.LocalSynchroniser;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.time.SuperSynchroniser;

public class AbstractConnectedPhysics extends AbstractPhysics {

	private LocalSynchroniser synchroniser;

	public AbstractConnectedPhysics() {
		super();
	}

	@Override
	public void simulate() {
		((SuperSynchroniser) synchroniser).simulate();
	}

	/**
	 * This method will take all {@link Action}s performed by {@link Agent}s in
	 * the local {@link Environment} and forward them to any {@link Environment}
	 * that is subscribed to that {@link Action}.
	 */
	public void propagateActions() {
		System.out.println("PROPAGATE ACTIONS: " + this.getId()
				+ System.lineSeparator() + "   "
				+ environment.getState().getCommunicationActions()
				+ System.lineSeparator() + "   "
				+ environment.getState().getSensingActions());
		this.getEnvironment().sendActions(
				environment.getState().getCommunicationActions());
		this.getEnvironment().sendActions(
				environment.getState().getSensingActions());
		// TODO physical actions
	}

	@Override
	public void executeActions() {
		this.getEnvironment().clearAndUpdateActionsAfterPropagation();
		System.out.println("EXECUTE ACTIONS: " + this.getId()
				+ System.lineSeparator() + "   "
				+ environment.getState().getCommunicationActions()
				+ System.lineSeparator() + "   "
				+ environment.getState().getSensingActions());
		super.executeActions();

	}

	/**
	 * Initialises the {@link LocalSynchroniser} for local sub and neighbour
	 * {@link Physics}.
	 * 
	 * @param subenvironments
	 * @param neighbouringenvironments
	 */
	public void initSynchroniser(
			Collection<AbstractConnectedEnvironment> subenvironments,
			Collection<AbstractConnectedEnvironment> neighbouringenvironments) {
		if (environment != null) {
			if (!Simulator.class.isAssignableFrom(environment.getClass())) {
				this.synchroniser = new LocalSynchroniser(
						(AbstractConnectedEnvironment) environment,
						subenvironments, neighbouringenvironments);
			} else {
				this.synchroniser = new SuperSynchroniser(
						(AbstractConnectedEnvironment) environment,
						subenvironments, neighbouringenvironments);
			}
		}
	}

	/**
	 * Initialises the {@link LocalSynchroniser} for remote {@link Physics}.
	 */
	public void initSynchroniser() {
		if (environment != null) {
			if (!Simulator.class.isAssignableFrom(environment.getClass())) {
				this.synchroniser = new LocalSynchroniser(
						(AbstractConnectedEnvironment) environment);
			} else {
				this.synchroniser = new SuperSynchroniser(
						(AbstractConnectedEnvironment) environment);
			}
		}
	}

	public LocalSynchroniser getSynchroniser() {
		return synchroniser;
	}

	@Override
	public AbstractConnectedEnvironment getEnvironment() {
		return (AbstractConnectedEnvironment) super.getEnvironment();
	}

}
