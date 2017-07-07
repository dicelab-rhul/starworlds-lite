package uk.ac.rhul.cs.dice.starworlds.entities.avatar;

import java.util.Collection;
import java.util.Stack;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractMind;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;

public abstract class AbstractAvatarMind<D extends Action> extends
		AbstractMind<D, Stack<D>> {

	protected Stack<D> actionsToExecute = new Stack<>();

	public AbstractAvatarMind() {
	}

	@Override
	public abstract Action cycle(Collection<Perception<?>> perceptions);

	public abstract void showAvatarView(Collection<Perception<?>> perceptions);

	@Override
	public Object perceive(Collection<Perception<?>> perceptions) {
		showAvatarView(perceptions);
		return null;
	}

	@Override
	public Boolean decide(D action) {
		actionsToExecute.push(action);
		return true;
	}

	public Boolean decide(D[] actions) {
		for (D d : actions) {
			actionsToExecute.push(d);
		}
		return true;
	}

	@Override
	public Action execute(Stack<D> actionstack) {
		if (!actionstack.isEmpty()) {
			return actionstack.pop();
		}
		return null;
	}
}
