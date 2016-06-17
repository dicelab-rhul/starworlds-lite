package uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Result;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObserver;

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
public abstract class AbstractPhysics extends CustomObservable implements Physics, CustomObserver {
	private Result currentMoveResult;

	/**
	 * Returns the {@link Result} of the current execution attempt.
	 * 
	 * @return Returns the {@link Result} of the current execution attempt.
	 */
	public Result getCurrentMoveResult() {
		return this.currentMoveResult;
	}

	/**
	 * Sets the {@link Result} of the current execution attempt.
	 * 
	 * @param currentMoveResult : the {@link Result} of the current execution attempt.
	 */
	public void setCurrentMoveResult(Result currentMoveResult) {
		this.currentMoveResult = currentMoveResult;
	}
}