package uk.ac.rhul.cs.dice.gawl.interfaces.environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Action;
import uk.ac.rhul.cs.dice.gawl.interfaces.common.ComplexEnvironmentAppearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Body;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;

/**
 * A subclass of {@link SimpleEnvironment} which can contain an arbitrary number of {@link AbstractEnvironment} instances as sub-environments.
 * Thus, the method {@link #isSimple()} will always return false.<br/><br/>
 * 
 * Known direct subclasses: {@link Universe}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Kostas Stathis
 *
 */
public abstract class ComplexEnvironment extends SimpleEnvironment {
	private List<AbstractEnvironment> subEnvironments;
	
	public ComplexEnvironment(EnvironmentalSpace state, Set<Action> admissibleActions, Set<Body> bodies, Physics physics, boolean bounded, ComplexEnvironmentAppearance appearance) {
		super(state, admissibleActions, bodies, physics, bounded, appearance);
		this.subEnvironments = new ArrayList<>();
	}
	
	public List<AbstractEnvironment> getSubEnvironments() {
		return this.subEnvironments;
	}
	
	public void addSubEnvironment(AbstractEnvironment environment) {
		this.subEnvironments.add(environment);
	}
	
	@Override
	public boolean isSimple() {
		return false;
	}
}