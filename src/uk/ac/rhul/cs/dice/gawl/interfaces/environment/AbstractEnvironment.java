package uk.ac.rhul.cs.dice.gawl.interfaces.environment;

import uk.ac.rhul.cs.dice.gawl.interfaces.appearances.Appearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;
import uk.ac.rhul.cs.dice.gawl.interfaces.perception.Perception;

/**
 * The most general class representing an environment. It has an {@link EnvironmentalSpace}, an instance of {@link Physics}, a {@link Boolean} indicating whether
 * it is bounded or not, and an {@link Appearance}.<br/><br/>
 * 
 * Known direct subclasses: {@link SimpleEnvironment}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class AbstractEnvironment<P extends Perception> implements Environment<P>, Container {
	private Space state;
	private Physics<P> physics;
	private Boolean bounded;
	private Appearance appearance;
	
	/**
	 * The default class constructor.
	 * 
	 * @param state : an {@link Space} instance.
	 * @param physics : the {@link Physics} of the environment.
	 * @param bounded : a {@link Boolean} value indicating whether the environment is bounded or not.
	 * @param appearance : the {@link Appearance} of the environment.
	 */
	public AbstractEnvironment(Space state, Physics<P> physics, Boolean bounded, Appearance appearance) {
		this.state = state;
		this.physics = physics;
		this.bounded = bounded;
		this.appearance = appearance;
	}

	@Override
	public Space getState() {
		return this.state;
	}

	@Override
	public void setState(Space state) {
		this.state = state;
	}

	@Override
	public Physics<P> getPhysics() {
		return this.physics;
	}

	@Override
	public void setPhysics(Physics<P> physics) {
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