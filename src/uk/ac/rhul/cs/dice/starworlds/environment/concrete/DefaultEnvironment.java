package uk.ac.rhul.cs.dice.starworlds.environment.concrete;

import uk.ac.rhul.cs.dice.starworlds.appearances.AbstractAppearance;
import uk.ac.rhul.cs.dice.starworlds.environment.State;
import uk.ac.rhul.cs.dice.starworlds.environment.Universe;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.Physics;

public class DefaultEnvironment extends Universe {

	public DefaultEnvironment(State state, Physics physics,
			AbstractAppearance appearance) {
		super(state, physics, appearance);
	}
}
