package uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Result;

/**
 * The most general class representing physics.<br/><br/>
 * 
 * Known direct subclasses: none.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Kostas Stathis
 *
 */
public abstract class AbstractPhysics implements Physics {
	private Result currentMoveResult;

	public Result getCurrentMoveResult() {
		return this.currentMoveResult;
	}

	public void setCurrentMoveResult(Result currentMoveResult) {
		this.currentMoveResult = currentMoveResult;
	}
}