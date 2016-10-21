package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents;

import java.util.List;
import java.util.Queue;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Result;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObserver;
import uk.ac.rhul.cs.dice.gawl.interfaces.perception.Perception;

/**
 * The interface for brains. it extends {@link CustomObserver}.<br/><br/>
 * 
 * Known implementations: {@link AbstractAgentBrain}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public interface Brain<P extends Perception> extends CustomObserver {
	public abstract void pushReceivedResultToQueue(Result<P> result);
	public abstract Result<P> pullReceivedResultFromQueue();
	public abstract void addResultToSendToList(Result<P> result);
	public abstract Queue<Result<P>> getReceivedResults();
	public abstract List<Result<P>> getResultsToSend();
	public abstract boolean isActionResultReturned();
	public abstract void setActionResultReturned(boolean flag);
	public abstract Class<? extends Mind<P>> getPairedMindClass();
	public abstract void updateResultsToSend();
	public abstract void manageMindPullRequest();
	public abstract void clearReceivedResults();
	public abstract void clearResultsToSend();
}