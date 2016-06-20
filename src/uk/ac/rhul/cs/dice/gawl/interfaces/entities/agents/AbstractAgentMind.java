package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents;

import java.util.ArrayList;
import java.util.List;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Action;
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
	private List<Action> availableActionsForThisCicle;

	@Override
	public List<Action> getAvailableActionsForThisCicle() {
		return this.availableActionsForThisCicle;
	}

	@Override
	public void addAvailableActionForThisCicle(Action availableActionForThisCicle) {
		if(this.availableActionsForThisCicle == null) {
			this.availableActionsForThisCicle = new ArrayList<>();
		}
		
		this.availableActionsForThisCicle.add(availableActionForThisCicle);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((availableActionsForThisCicle == null) ? 0 : availableActionsForThisCicle.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractAgentMind other = (AbstractAgentMind) obj;
		if (availableActionsForThisCicle == null) {
			if (other.availableActionsForThisCicle != null)
				return false;
		} else if (!availableActionsForThisCicle.equals(other.availableActionsForThisCicle))
			return false;
		return true;
	}
}