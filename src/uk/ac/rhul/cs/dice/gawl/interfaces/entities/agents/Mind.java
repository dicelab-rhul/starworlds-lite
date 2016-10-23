package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents;

import java.util.List;
import java.util.Random;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.EnvironmentalAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Result;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObserver;
import uk.ac.rhul.cs.dice.gawl.interfaces.perception.Perception;

/**
 * The interface for minds. it extends {@link CustomObserver}.<br/><br/>
 * 
 * Known implementations: {@link AbstractAgentMind}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public interface Mind extends CustomObserver {
	public abstract void perceive(Object perceptionWrapper);
	public abstract EnvironmentalAction decide(Object... parameters);
	public abstract void execute(EnvironmentalAction action);
	public abstract List<Class<? extends EnvironmentalAction>> getAvailableActionsForThisCycle();
	public abstract void addAvailableActionForThisCycle(Class<? extends EnvironmentalAction> availableActionForThisCycle);
	public abstract void loadAvailableActionsForThisCycle(List<Class<? extends EnvironmentalAction>> availableActionsForThisCycle);
	public abstract void loadAvailableActionsForThisMindFromArbitraryParameters(Object... mindActions);
	public abstract void loadAvailableActionForThisMind(Class<? extends EnvironmentalAction> mindAction);
	public abstract List<Class<? extends EnvironmentalAction>> getAvailableActionsForThisMind();
	public abstract Class<? extends EnvironmentalAction> decideActionPrototypeRandomly();
	public abstract EnvironmentalAction decideActionRandomly();
	public abstract Random getRNG();
	public abstract Object getBodyId();
	public abstract void setBodyId(Object bodyId);
	public abstract boolean lastActionSucceeded();
	public abstract boolean lastActionFailed();
	public abstract boolean wasLastActionImpossible();
	public abstract int getPerceptionRange();
	public abstract boolean canSeeBehind();
	public abstract void setCanSeeBehind(boolean canSeeBehind);
	public abstract void setPerceptionRange(int preceptionRange);
	public abstract EnvironmentalAction getNextAction();
	public abstract void setNextActionForExecution(EnvironmentalAction action);
	public abstract Result getLastActionResult();
	public abstract void setLastActionResult(Result result);
	public abstract Perception getPerception();
	public abstract List<Result> getReceivedCommunications();
	public abstract void addReceivedCommunicationToList(Result communicationResult);
	public abstract void clearReceivedCommunications();
}