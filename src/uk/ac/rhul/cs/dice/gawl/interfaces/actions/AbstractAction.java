package uk.ac.rhul.cs.dice.gawl.interfaces.actions;

import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Actor;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.EnvironmentalSpace;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;

/**
 * The most generic action class implementing {@link Action}.<br/><br/>
 * 
 * Known direct subclasses: {@link PhysicalAction}, {@link CommunicationAction}, {@link SensingAction}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Kostas Stathis
 *
 */
public abstract class AbstractAction implements Action {
	private Actor actor;
	
	public AbstractAction() {}
	
	public AbstractAction(Actor actor) {
		this.actor = actor;
	}
	
	public Actor getActor() {
		return this.actor;
	}
	
	public void setActor(Actor actor) {
		this.actor = actor;
	}
	
	@Override
	public boolean isPossible(Physics physics, EnvironmentalSpace context) {
		return physics.isPossible(this, context);
	}
	
	@Override
	public boolean isNecessary(Physics physics, EnvironmentalSpace context) {
		return physics.isNecessary(this, context);
	}
	
	@Override
	public Result attempt(Physics physics, EnvironmentalSpace context) {
		Result result = new DefaultActionResult(ActionResult.ACTION_IMPOSSIBLE);
		
		if(isPossible(physics, context)) {
			result = perform(physics, context);
			result = checkResultSoundness(result, physics, context);
		}
		
		return result;
	}
	
	private Result checkResultSoundness(Result result, Physics physics, EnvironmentalSpace context) {
		if(result.getActionResult() == ActionResult.ACTION_FAILED) {
			return result;
		}
		
		if(succeeded(physics, context)) {
			return new DefaultActionResult(ActionResult.ACTION_DONE);
		}
		else {
			return new DefaultActionResult(ActionResult.ACTION_FAILED);
		}
	}

	@Override
	public Result perform(Physics physics, EnvironmentalSpace context) {
		return physics.perform(this, context);
	}
	
	@Override
	public boolean succeeded(Physics physics, EnvironmentalSpace context) {
		return physics.succeeded(this, context);
	}
}