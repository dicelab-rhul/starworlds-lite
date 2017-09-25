package uk.ac.rhul.cs.dice.starworlds.entities.avatar;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;

public abstract class AbstractSelfishAvatarMind<D extends Action> extends
		AbstractAvatarMind<D> {

	private volatile Boolean decided = false;

	@Override
	public final Action cycle(Collection<Perception<?>> perceptions) {
		this.decided = !actionsToExecute.isEmpty();
		this.perceive(perceptions);
		// wait for the decide method to be called.
		while (!(decided || !linked))
			;
		// the user has decide
		return this.execute(actionsToExecute);
	}

	@Override
	public Boolean decide(D action) {
		Boolean d = super.decide(action);
		this.decided = true;
		return d;
	}

	@Override
	public Boolean decide(D[] actions) {
		Boolean d = super.decide(actions);
		this.decided = true;
		return d;
	}
}
