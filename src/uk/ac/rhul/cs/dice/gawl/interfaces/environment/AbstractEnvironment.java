package uk.ac.rhul.cs.dice.gawl.interfaces.environment;

import java.util.HashSet;
import java.util.Set;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Action;
import uk.ac.rhul.cs.dice.gawl.interfaces.common.Appearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Body;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;

/**
 * The most general class representing an environment. It has an {@link EnvironmentalSpace}, a {@link Set} of admissible {@link Action}
 * elements and one of {@link Body} elements. It also contains an instance of {@link Physics}, a {@link Boolean} indicating whether
 * it is bounded or not, and an {@link Appearance}.<br/><br/>
 * 
 * Known direct subclasses: {@link SimpleEnvironment}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Kostas Stathis
 *
 */
public abstract class AbstractEnvironment implements Environment, Container {
	private EnvironmentalSpace state;
	private Set<Action> admissibleActions;
	private Set<Body> bodies;
	private Physics physics;
	private boolean bounded;
	private Appearance appearance;
	
	public AbstractEnvironment(EnvironmentalSpace state, Set<Action> admissibleActions, Set<Body> bodies, Physics physics, boolean bounded, Appearance appearance) {
		this.state = state;
		this.admissibleActions = admissibleActions != null ? admissibleActions : new HashSet<>();
		this.bodies = bodies != null ? bodies : new HashSet<>();
		this.physics = physics;
		this.bounded = bounded;
		this.appearance = appearance;
	}

	@Override
	public EnvironmentalSpace getState() {
		return this.state;
	}

	@Override
	public void setState(EnvironmentalSpace state) {
		this.state = state;
	}

	@Override
	public Set<Action> getAdmissibleActions() {
		return this.admissibleActions;
	}

	@Override
	public void setAdmissibleActions(Set<Action> admissibleActions) {
		this.admissibleActions = admissibleActions;
	}

	@Override
	public Set<Body> getBodies() {
		return this.bodies;
	}

	@Override
	public void setBodies(Set<Body> bodies) {
		this.bodies = bodies;
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
	public boolean isBounded() {
		return this.bounded;
	}

	@Override
	public void setBounded(boolean bounded) {
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