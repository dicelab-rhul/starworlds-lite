package uk.ac.rhul.cs.dice.starworlds.environment;

import java.util.Collection;
import java.util.List;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.Appearance;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.Physics;

public class DistributedComplexEnvironment extends DistributedEnvironment {

	private List<AbstractEnvironment<?>> subEnvironments;

	public DistributedComplexEnvironment(
			State state,
			Physics physics,
			Boolean bounded,
			Appearance appearance,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions,
			int port) {
		super(state, physics, bounded, appearance, possibleActions, port);

	}

	/**
	 * Returns the {@link List} of sub-environments.
	 * 
	 * @return a {@link List} of {@link AbstractEnvironment} elements.
	 */
	public List<AbstractEnvironment<?>> getSubEnvironments() {
		return this.subEnvironments;
	}

	/**
	 * Adds an {@link AbstractEnvironment} to the {@link List} of
	 * sub-environments.
	 * 
	 * @param environment
	 *            : the {@link AbstractEnvironment} to add to the {@link List}
	 *            of sub-environments.
	 */
	public void addSubEnvironment(AbstractEnvironment<?> environment) {
		this.subEnvironments.add(environment);
	}

	@Override
	public Boolean isSimple() {
		return false;
	}

	@Override
	public boolean isDistributed() {
		return true;
	}

}
