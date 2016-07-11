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
	private Result currentResult;

	/**
	 * Returns the {@link Result} of the current execution attempt.
	 * 
	 * @return Returns the {@link Result} of the current execution attempt.
	 */
	public Result getCurrentResult() {
		return this.currentResult;
	}

	/**
	 * Sets the {@link Result} of the current execution attempt.
	 * 
	 * @param currentMoveResult : the {@link Result} of the current execution attempt.
	 */
	public void setCurrentResult(Result currentMoveResult) {
		this.currentResult = currentMoveResult;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((currentResult == null) ? 0 : currentResult.hashCode());
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
		if (currentResult == null) {
			if (other.currentResult != null)
				return false;
		} else if (!currentResult.equals(other.currentResult))
			return false;
		return true;
	}
}