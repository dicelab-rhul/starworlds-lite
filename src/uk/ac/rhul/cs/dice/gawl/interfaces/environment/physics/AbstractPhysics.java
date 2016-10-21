package uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Result;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObserver;
import uk.ac.rhul.cs.dice.gawl.interfaces.perception.Perception;

/**
 * The most general class representing physics. It extends {@link CustomObservable} and it implements {@link Physics}
 * and {@link CustomObserver}.<br/><br/>
 * 
 * Known direct subclasses: none.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class AbstractPhysics<P extends Perception> extends CustomObservable implements Physics<P>, CustomObserver {
	private Result<P> currentResult;

	/**
	 * Returns the {@link Result} of the current execution attempt.
	 * 
	 * @return Returns the {@link Result} of the current execution attempt.
	 */
	@Override
	public Result<P> getCurrentResult() {
		return this.currentResult;
	}

	/**
	 * Sets the {@link Result} of the current execution attempt.
	 * 
	 * @param currentMoveResult : the {@link Result} of the current execution attempt.
	 */
	@Override
	public void setCurrentResult(Result<P> currentMoveResult) {
		this.currentResult = currentMoveResult;
	}
}