package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Action;

/**
 * A {@link Mind} implementation which is {@link Observable}.<br/><br/>
 * 
 * Known direct subclasses: {@link AutonomousAgentMind}, {@link AvatarMind}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Kostas Stathis
 *
 */
public abstract class AbstractAgentMind extends Observable implements Mind {
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