package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents;

import java.util.ArrayList;
import java.util.List;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;

/**
 * A {@link Mind} implementation which extends {@link CustomObservable}.<br/><br/>
 * 
 * Known direct subclasses: {@link AutonomousAgentMind}, {@link AvatarMind}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class AbstractAgentMind extends CustomObservable implements Mind {
	private List<Class<? extends AbstractAction>> availableActionsForThisCycle;

	@Override
	public List<Class<? extends AbstractAction>> getAvailableActionsForThisCycle() {
		return this.availableActionsForThisCycle;
	}

	@Override
	public void addAvailableActionForThisCycle(Class<? extends AbstractAction> availableActionForThisCycle) {
		if(this.availableActionsForThisCycle == null) {
			this.availableActionsForThisCycle = new ArrayList<>();
		}
		
		this.availableActionsForThisCycle.add(availableActionForThisCycle);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((availableActionsForThisCycle == null) ? 0 : availableActionsForThisCycle.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractAgentMind other = (AbstractAgentMind) obj;
		if (availableActionsForThisCycle == null) {
			if (other.availableActionsForThisCycle != null)
				return false;
		} else if (!availableActionsForThisCycle.equals(other.availableActionsForThisCycle))
			return false;
		return true;
	}
}