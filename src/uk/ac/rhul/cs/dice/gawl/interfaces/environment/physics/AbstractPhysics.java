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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((currentMoveResult == null) ? 0 : currentMoveResult.hashCode());
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
		AbstractPhysics other = (AbstractPhysics) obj;
		if (currentMoveResult == null) {
			if (other.currentMoveResult != null)
				return false;
		} else if (!currentMoveResult.equals(other.currentMoveResult))
			return false;
		return true;
	}
}