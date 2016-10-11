package uk.ac.rhul.cs.dice.gawl.interfaces.actions;

import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Actor;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Space;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;

/**
 * The most generic action class implementing {@link EnvironmentalAction}.<br/><br/>
 * 
 * Known direct subclasses: {@link PhysicalAction}, {@link CommunicationAction}, {@link SensingAction}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class AbstractAction implements EnvironmentalAction {
	private Actor actor;
	
	/**
	 * The default constructor.
	 */
	public AbstractAction() {}
	
	/**
	 * The constructor with an {@link Actor}.
	 * 
	 * @param actor : the {@link Actor} of the {@link EnvironmentalAction}.
	 */
	public AbstractAction(Actor actor) {
		this.actor = actor;
	}
	
	/**
	 * Returns the {@link Actor} of the {@link EnvironmentalAction}.
	 * 
	 * @return the {@link Actor} of the {@link EnvironmentalAction}.
	 */
	public Actor getActor() {
		return this.actor;
	}
	
	/**
	 * Sets the {@link Actor} of the {@link EnvironmentalAction}.
	 * 
	 * @param actor : the {@link Actor} of the {@link EnvironmentalAction}.
	 */
	public void setActor(Actor actor) {
		this.actor = actor;
	}
	
	@Override
	public Result attempt(Physics physics, Space context) {
		Result result = new DefaultActionResult(ActionResult.ACTION_IMPOSSIBLE, null);
		
		if(isPossible(physics, context)) {
			result = perform(physics, context);
			result = checkResultSoundness(result, physics, context);
		}
		
		return result;
	}

	private Result checkResultSoundness(Result result, Physics physics, Space context) {
		if(result.getActionResult() == ActionResult.ACTION_FAILED) {
			return result;
		}
		
		if(succeeded(physics, context)) {
			return result;
		}
		else {
			return new DefaultActionResult(ActionResult.ACTION_FAILED, null);
		}
	}
}