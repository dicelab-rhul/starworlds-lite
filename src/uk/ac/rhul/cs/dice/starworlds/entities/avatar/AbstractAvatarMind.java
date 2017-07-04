package uk.ac.rhul.cs.dice.starworlds.entities.avatar;

import java.util.Collection;
import java.util.Stack;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractMind;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;

public abstract class AbstractAvatarMind<D extends Action> extends
		AbstractMind<D, Stack<D>> {

	private Stack<D> actionsToExecute = new Stack<>();
	private volatile Boolean decided = false;

	public AbstractAvatarMind() {
	}

	public abstract void showAvatarView(Collection<Perception<?>> perceptions);

	@Override
	public Action cycle(Collection<Perception<?>> perceptions) {
		this.decided = false;
		this.perceive(perceptions);
		// wait for the decide method to be called.
		while (!decided)
			;
		// the user has decide
		Action actionToExecute = this.execute(actionsToExecute);
		return actionToExecute;
	}

	@Override
	public Object perceive(Collection<Perception<?>> perceptions) {
		showAvatarView(perceptions);
		return null;
	}

	@Override
	public Boolean decide(D action) {
		actionsToExecute.push(action);
		this.decided = true;
		return true;
	}

	@Override
	public Action execute(Stack<D> actionstack) {
		return actionstack.pop();
	}
}
