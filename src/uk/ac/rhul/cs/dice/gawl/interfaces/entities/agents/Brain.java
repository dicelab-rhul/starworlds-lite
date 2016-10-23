package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents;

import java.util.List;
import java.util.Queue;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Result;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObserver;

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
public interface Brain extends CustomObserver {
	public abstract void pushReceivedResultToQueue(Result result);
	public abstract Result pullReceivedResultFromQueue();
	public abstract void addResultToSendToList(Result result);
	public abstract Queue<Result> getReceivedResults();
	public abstract List<Result> getResultsToSend();
	public abstract boolean isActionResultReturned();
	public abstract void setActionResultReturned(boolean flag);
	public abstract Class<? extends Mind> getPairedMindClass();
	public abstract void updateResultsToSend();
	public abstract void manageMindPullRequest();
	public abstract void clearReceivedResults();
	public abstract void clearResultsToSend();
}