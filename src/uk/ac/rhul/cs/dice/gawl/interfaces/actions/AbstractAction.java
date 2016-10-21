package uk.ac.rhul.cs.dice.gawl.interfaces.actions;

import java.util.ArrayList;

import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Actor;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Space;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;
import uk.ac.rhul.cs.dice.gawl.interfaces.perception.Perception;

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
public abstract class AbstractAction<P extends Perception> implements EnvironmentalAction<P> {
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
	public Result<P> attempt(Physics<P> physics, Space context) {
		Result<P> result = new DefaultActionResult<>(ActionResult.ACTION_IMPOSSIBLE, getActor().getId().toString(), new ArrayList<>(), null);
		
		if(isPossible(physics, context)) {
			result = perform(physics, context);
			result = checkResultSoundness(result, physics, context);
		}
		
		return result;
	}

	private Result<P> checkResultSoundness(Result<P> result, Physics< P> physics, Space context) {
		if(result.getActionResult() == ActionResult.ACTION_FAILED) {
			return result;
		}
		
		if(succeeded(physics, context)) {
			return result;
		}
		else {
			return new DefaultActionResult<>(ActionResult.ACTION_FAILED, getActor().getId().toString(), result.getFailureReason(), new ArrayList<>());
		}
	}
	
	@Override
	public boolean isPossible(Physics<P> physics, Space context) {
		return physics.isPossible(this, context);
	}
	
	@Override
	public boolean isNecessary(Physics<P> physics, Space context) {
		return physics.isNecessary(this, context);
	}
	
	@Override
	public Result<P> perform(Physics<P> physics, Space context) {
		return physics.perform(this, context);
	}
	
	@Override
	public boolean succeeded(Physics<P> physics, Space context) {
		return physics.succeeded(this, context);
	}
}