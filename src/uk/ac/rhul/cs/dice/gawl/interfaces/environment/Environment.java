package uk.ac.rhul.cs.dice.gawl.interfaces.environment;

import java.util.Set;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Action;
import uk.ac.rhul.cs.dice.gawl.interfaces.common.Appearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Body;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;

/**
 * The most general interface exposing the essential methods all the environments need to implement.<br/><br/>
 * 
 * Known implementations: {@link AbstractEnvironment}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Kostas Stathis
 *
 */
public interface Environment {
	/**
	 * Returns the {@link EnvironmentalSpace} of the {@link Environment}.
	 * 
	 * @return the {@link EnvironmentalSpace} of the {@link Environment}.
	 */
	public EnvironmentalSpace getState();
	
	/**
	 * Sets the {@link EnvironmentalSpace} of the {@link Environment}.
	 * 
	 * @param state : the {@link EnvironmentalSpace} of the {@link Environment}.
	 */
	public void setState(EnvironmentalSpace state);
	
	/**
	 * Returns the {@link Set} of {@link Action} instances which are performable in the {@link Environment}.
	 * 
	 * @return the {@link Set} of {@link Action} instances which are performable in the {@link Environment}.
	 */
	public Set<Action> getAdmissibleActions();
	
	/**
	 * Sets the {@link Set} of {@link Action} instances which are performable in the {@link Environment}.
	 * 
	 * @param admissibleActions : the {@link Set} of {@link Action} instances which are performable in the {@link Environment}.
	 */
	public void setAdmissibleActions(Set<Action> admissibleActions);
	
	/**
	 * Returns the {@link Set} of bodies present in the {@link Environment}.
	 * 
	 * @return the {@link Set} of bodies present in the {@link Environment}.
	 */
	public Set<Body> getBodies();
	
	/**
	 * Adds a {@link Set} of bodies to the {@link Environment}.
	 * 
	 * @param bodies : the {@link Set} of bodies to be added to the {@link Environment}.
	 */
	public void setBodies(Set<Body> bodies);
	
	/**
	 * Returns the {@link Physics} of the {@link Environment}.
	 * 
	 * @return the {@link Physics} of the {@link Environment}.
	 */
	public Physics getPhysics();
	
	/**
	 * Sets the {@link Physics} of the {@link Environment}.
	 * 
	 * @param physics : the {@link Physics} of the {@link Environment}.
	 */
	public void setPhysics(Physics physics);
	
	/**
	 * Sets whether the {@link Environment} is bounded or not.
	 * 
	 * @param bounded : a {@link Boolean} value representing whether the {@link Environment} is bounded or not.
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
	 * @param appearance : the {@link Appearance} of the {@link Environment}.
	 */
	public void setAppearance(Appearance appearance);
	
	/**
	 * Checks whether the {@link Environment} is simple or not.
	 * 
	 * @return a {@link Boolean} value representing whether the {@link Environment} is simple or not.
	 */
	public Boolean isSimple();
	
	/**
	 * Checks whether the {@link Environment} is bounded or not.
	 * 
	 * @return a {@link Boolean} value representing whether the {@link Environment} is bounded or not.
	 */
	public Boolean isBounded();
}