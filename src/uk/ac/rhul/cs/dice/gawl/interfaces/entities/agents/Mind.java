package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents;

import java.util.List;
import java.util.Observer;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Action;

/**
 * The interface for minds. it extends {@link Observer}.<br/><br/>
 * 
 * Known implementations: {@link AbstractAgentMind}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Kostas Stathis
 *
 */
public interface Mind extends Observer {
	public Action decide(Object... parameters);
	public List<Action> getAvailableActionsForThisCicle();
	public void addAvailableActionForThisCicle(Action availableActionForThisCicle);
}