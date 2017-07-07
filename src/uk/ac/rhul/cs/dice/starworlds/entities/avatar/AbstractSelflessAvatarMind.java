package uk.ac.rhul.cs.dice.starworlds.entities.avatar;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;

public abstract class AbstractSelflessAvatarMind<D extends Action> extends
		AbstractAvatarMind<D> {

	@Override
	public final Action cycle(Collection<Perception<?>> perceptions) {
		this.perceive(perceptions);
		// wait for the decide method to be called.
		// don't wait for the user to decide, this may execute nothing!
		// the user has decide
		return this.execute(actionsToExecute);
	}
}
