package uk.ac.rhul.cs.dice.gawl.interfaces.environment;

import uk.ac.rhul.cs.dice.gawl.interfaces.appearances.Appearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;

/**
 * The most general interface exposing the essential methods all the environments need to implement.<br/><br/>
 * 
 * Known implementations: {@link AbstractEnvironment}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public interface Environment {
	/**
	 * Returns the {@link Space} of the {@link Environment}.
	 * 
	 * @return the {@link Space} of the {@link Environment}.
	 */
	public abstract Space getState();
	
	/**
	 * Sets the {@link Space} of the {@link Environment}.
	 * 
	 * @param state : the {@link Space} of the {@link Environment}.
	 */
	public abstract void setState(Space state);
	
	/**
	 * Returns the {@link Physics} of the {@link Environment}.
	 * 
	 * @return the {@link Physics} of the {@link Environment}.
	 */
	public abstract Physics getPhysics();
	
	/**
	 * Sets the {@link Physics} of the {@link Environment}.
	 * 
	 * @param physics : the {@link Physics} of the {@link Environment}.
	 */
	public abstract void setPhysics(Physics physics);
	
	/**
	 * Sets whether the {@link Environment} is bounded or not.
	 * 
	 * @param bounded : a {@link Boolean} value representing whether the {@link Environment} is bounded or not.
	 */
	public abstract void setBounded(Boolean bounded);
	
	/**
	 * Returns the {@link Appearance} of the {@link Environment}.
	 * 
	 * @return the {@link Appearance} of the {@link Environment}.
	 */
	public abstract Appearance getAppearance();
	
	/**
	 * Sets the {@link Appearance} of the {@link Environment}.
	 * 
	 * @param appearance : the {@link Appearance} of the {@link Environment}.
	 */
	public abstract void setAppearance(Appearance appearance);
	
	/**
	 * Checks whether the {@link Environment} is simple or not.
	 * 
	 * @return a {@link Boolean} value representing whether the {@link Environment} is simple or not.
	 */
	public abstract Boolean isSimple();
	
	/**
	 * Checks whether the {@link Environment} is bounded or not.
	 * 
	 * @return a {@link Boolean} value representing whether the {@link Environment} is bounded or not.
	 */
	public abstract Boolean isBounded();
}