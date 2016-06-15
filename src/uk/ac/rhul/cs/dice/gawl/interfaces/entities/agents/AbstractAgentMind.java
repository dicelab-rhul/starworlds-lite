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
}