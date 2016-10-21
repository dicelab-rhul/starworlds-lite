package uk.ac.rhul.cs.dice.gawl.interfaces.environment;

import java.util.Set;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.EnvironmentalAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.appearances.Appearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Body;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;
import uk.ac.rhul.cs.dice.gawl.interfaces.perception.Perception;

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
public interface Environment<P extends Perception> {
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
	 * Returns the {@link Set} of {@link AbstractAction} instances which are performable in the {@link Environment}.
	 * 
	 * @return the {@link Set} of {@link AbstractAction} instances which are performable in the {@link Environment}.
	 */
	public abstract Set<Class<? extends EnvironmentalAction<P>>> getAdmissibleActions();
	
	/**
	 * Sets the {@link Set} of {@link AbstractAction} instances which are performable in the {@link Environment}.
	 * 
	 * @param admissibleActions : the {@link Set} of {@link AbstractAction} instances which are performable in the {@link Environment}.
	 */
	public abstract void setAdmissibleActions(Set<Class<? extends EnvironmentalAction<P>>> admissibleActions);
	
	/**
	 * Returns the {@link Set} of bodies present in the {@link Environment}.
	 * 
	 * @return the {@link Set} of bodies present in the {@link Environment}.
	 */
	public abstract Set<Body> getBodies();
	
	/**
	 * Adds a {@link Set} of bodies to the {@link Environment}.
	 * 
	 * @param bodies : the {@link Set} of bodies to be added to the {@link Environment}.
	 */
	public abstract void setBodies(Set<Body> bodies);
	
	/**
	 * Returns the {@link Physics} of the {@link Environment}.
	 * 
	 * @return the {@link Physics} of the {@link Environment}.
	 */
	public abstract Physics<P> getPhysics();
	
	/**
	 * Sets the {@link Physics} of the {@link Environment}.
	 * 
	 * @param physics : the {@link Physics} of the {@link Environment}.
	 */
	public abstract void setPhysics(Physics<P> physics);
	
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