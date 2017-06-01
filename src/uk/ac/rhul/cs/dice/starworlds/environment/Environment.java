package uk.ac.rhul.cs.dice.starworlds.environment;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.Appearance;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.Physics;

/**
 * The most general interface exposing the essential methods all the
 * environments need to implement.<br/>
 * <br/>
 * 
 * Known implementations: {@link AbstractEnvironment}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public interface Environment {

	public void updateState(AbstractEnvironmentalAction action);

	/**
	 * Returns the {@link State} of the {@link Environment}.
	 * 
	 * @return the {@link State} of the {@link Environment}.
	 */
	public State getState();

	/**
	 * Sets the {@link State} of the {@link Environment}.
	 * 
	 * @param state
	 *            : the {@link State} of the {@link Environment}.
	 */
	public void setState(State state);

	/**
	 * Returns the {@link Physics} of the {@link Environment}.
	 * 
	 * @return the {@link Physics} of the {@link Environment}.
	 */
	public Physics getPhysics();

	/**
	 * Sets the {@link Physics} of the {@link Environment}.
	 * 
	 * @param physics
	 *            : the {@link Physics} of the {@link Environment}.
	 */
	public void setPhysics(Physics physics);

	/**
	 * Sets whether the {@link Environment} is bounded or not.
	 * 
	 * @param bounded
	 *            : a {@link Boolean} value representing whether the
	 *            {@link Environment} is bounded or not.
	 */
	public void setBounded(Boolean bounded);

	/**
	 * Returns the {@link Appearance} of the {@link Environment}.
	 * 
	 * @return the {@link Appearance} of the {@link Environment}.
	 */
	public Appearance getAppearance();

	/**
	 * Sets the {@link Appearance} of the {@link Environment}.
	 * 
	 * @param appearance
	 *            : the {@link Appearance} of the {@link Environment}.
	 */
	public void setAppearance(Appearance appearance);

	/**
	 * Checks whether the {@link Environment} is simple or not.
	 * 
	 * @return a {@link Boolean} value representing whether the
	 *         {@link Environment} is simple or not.
	 */
	public Boolean isSimple();

	/**
	 * Checks whether the {@link Environment} is bounded or not.
	 * 
	 * @return a {@link Boolean} value representing whether the
	 *         {@link Environment} is bounded or not.
	 */
	public Boolean isBounded();
}