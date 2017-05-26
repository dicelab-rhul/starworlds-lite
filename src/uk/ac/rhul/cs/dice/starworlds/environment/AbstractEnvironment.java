package uk.ac.rhul.cs.dice.starworlds.environment;

import java.util.Arrays;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.Appearance;
import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.Physics;

/**
 * The most general class representing an environment. It has an
 * {@link EnvironmentalSpace}, an instance of {@link Physics}, a {@link Boolean}
 * indicating whether it is bounded or not, and an {@link Appearance}.<br/>
 * <br/>
 * 
 * Known direct subclasses: {@link SimpleEnvironment}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class AbstractEnvironment implements Environment, Container {

	private State state;
	private Physics physics;
	private Boolean bounded;
	private Appearance appearance;

	/**
	 * The default class constructor.
	 * 
	 * @param state
	 *            : an {@link State} instance.
	 * @param physics
	 *            : the {@link Physics} of the environment.
	 * @param bounded
	 *            : a {@link Boolean} value indicating whether the environment
	 *            is bounded or not.
	 * @param appearance
	 *            : the {@link Appearance} of the environment.
	 */
	public AbstractEnvironment(State state, Physics physics, Boolean bounded,
			Appearance appearance) {
		this.state = state;
		this.physics = physics;
		this.bounded = bounded;
		this.appearance = appearance;
		this.physics.setEnvironment(this);
		this.physics.getAgents().forEach((AbstractAgent agent) -> {
			agent.setEnvironment(this);
		});
		this.physics.getActiveBodies().forEach((ActiveBody body) -> {
			body.setEnvironment(this);
		});
	}

	@Override
	public synchronized void updateState(AbstractEnvironmentalAction action) {
		// System.out.println("UPDATING STATE WITH: " + action);
		state.filterAction(action);
		// System.out.println(Arrays.toString(state.getSensingActions().toArray()));
	}

	@Override
	public State getState() {
		return this.state;
	}

	@Override
	public void setState(State state) {
		this.state = state;
	}

	@Override
	public Physics getPhysics() {
		return this.physics;
	}

	@Override
	public void setPhysics(Physics physics) {
		this.physics = physics;
	}

	@Override
	public Boolean isBounded() {
		return this.bounded;
	}

	@Override
	public void setBounded(Boolean bounded) {
		this.bounded = bounded;
	}

	@Override
	public Appearance getAppearance() {
		return this.appearance;
	}

	@Override
	public void setAppearance(Appearance appearance) {
		this.appearance = appearance;
	}
}