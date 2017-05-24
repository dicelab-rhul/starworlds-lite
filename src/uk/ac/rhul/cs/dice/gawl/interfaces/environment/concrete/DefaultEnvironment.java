package uk.ac.rhul.cs.dice.gawl.interfaces.environment.concrete;

import uk.ac.rhul.cs.dice.gawl.interfaces.appearances.AbstractAppearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.State;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Universe;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;

public class DefaultEnvironment extends Universe {

	public DefaultEnvironment(State state, Physics physics,
			AbstractAppearance appearance) {
		super(state, physics, appearance);
	}
}
