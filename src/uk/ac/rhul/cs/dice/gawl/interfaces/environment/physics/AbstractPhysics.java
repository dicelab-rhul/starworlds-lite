package uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Action;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Result;

/**
 * The most general class representing physics.<br/><br/>
 * 
 * Known direct subclasses: //TODO
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Kostas Stathis
 *
 */
public abstract class AbstractPhysics implements Physics {
	
	@Override
	public boolean isPossible(Action action) {
		return action.isPossible(this);
	}
	@Override
	public boolean isNecessary(Action action) {
		return action.isNecessary(this);
	}
	
	@Override
	public Result perform(Action action) {
		return action.perform(this);
	}
	
	@Override
	public boolean succeeded(Action action) {
		return action.succeeded(this);
	}
}