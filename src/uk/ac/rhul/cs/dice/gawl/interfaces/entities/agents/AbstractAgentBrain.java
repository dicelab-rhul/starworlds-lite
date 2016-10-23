package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Result;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;

/**
 * A {@link Brain} implementation which is {@link CustomObservable}.<br/><br/>
 * 
 * Known direct subclasses: none.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class AbstractAgentBrain extends CustomObservable implements Brain {
	private Queue<Result> receivedResults;
	private List<Result> resultsToSend;
	private boolean actionResultReturned;
	private Class<? extends Mind> mindClass;
	
	public AbstractAgentBrain(Class<? extends Mind> mindClass) {
		this.mindClass = mindClass;
		this.receivedResults = new ConcurrentLinkedQueue<>();
		this.resultsToSend = new ArrayList<>();
		this.actionResultReturned = false;
	}
	
	@Override
	public void addResultToSendToList(Result result) {
		this.resultsToSend.add(result);
	}
	
	@Override
	public Class<? extends Mind> getPairedMindClass() {
		return this.mindClass;
	}
	
	@Override
	public Queue<Result> getReceivedResults() {
		return this.receivedResults;
	}
	
	@Override
	public List<Result> getResultsToSend() {
		return this.resultsToSend;
	}
	
	@Override
	public boolean isActionResultReturned() {
		return this.actionResultReturned;
	}
	
	@Override
	public Result pullReceivedResultFromQueue() {
		return this.receivedResults.poll();
	}
	
	@Override
	public void pushReceivedResultToQueue(Result result) {
		this.receivedResults.add(result);
	}
	
	@Override
	public void updateResultsToSend() {
		while (!this.receivedResults.isEmpty()) {
			this.resultsToSend.add(this.receivedResults.poll());
		}
	}
	
	@Override
	public void clearReceivedResults() {
		this.receivedResults.clear();
	}
	
	@Override
	public void clearResultsToSend() {
		this.resultsToSend.clear();
	}
	
	@Override
	public void setActionResultReturned(boolean flag) {
		this.actionResultReturned = flag;
	}
}