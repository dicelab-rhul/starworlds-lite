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
	public EnvironmentalSpace getState();
	public void setState(EnvironmentalSpace state);
	public Set<Action> getAdmissibleActions();
	public void setAdmissibleActions(Set<Action> admissibleActions);
	public Set<Body> getBodies();
	public void setBodies(Set<Body> bodies);
	public Physics getPhysics();
	public void setPhysics(Physics physics);
	public void setBounded(boolean bounded);
	public Appearance getAppearance();
	public void setAppearance(Appearance appearance);
	public boolean isSimple();
	public boolean isBounded();
}