package uk.ac.rhul.cs.dice.gawl.interfaces.actions;

import java.util.List;

import uk.ac.rhul.cs.dice.gawl.interfaces.perception.Perception;

/**
 * The interface for generic action results.<br/><br/>
 * 
 * Known implementations: {@link AbstractActionResult}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public interface Result {
	/**
	 * Returns the {@link String} actorId of the actor attempting the event this result refers to.
	 * 
	 * @return the {@link String} actorId of the actor attempting the event this result refers to.
	 */
	public abstract String getActorId();
	
	/**
	 * Returns the {@link ActionResult} wrapped by this object.
	 * 
	 * @return the {@link ActionResult} wrapped by this object.
	 */
	public abstract ActionResult getActionResult();
	
	/**
	 * Updates the {@link ActionResult} wrapped by this object with a new one.
	 * 
	 * @param newResult : the new {@link ActionResult}.
	 */
	public abstract void changeActionResult(ActionResult newResult);
	
	/**
	 * Returns the {@link Exception} wrapped by this object.
	 * 
	 * @return the {@link Exception} wrapped by this object.
	 */
	public abstract Exception getFailureReason();
	
	/**
	 * Returns the {@link List} of {@link String} IDs of the objects which will receive this {@link Result}.
	 * 
	 * @return the {@link List} of {@link String} IDs of the objects which will receive this {@link Result}.
	 */
	public abstract List<String> getRecipientsIds();
	
	/**
	 * Sets the {@link List} of {@link String} IDs of the objects which will receive this {@link Result}.
	 * 
	 * @param recipientsIds : the {@link List} of {@link String} IDs of the objects which will receive this {@link Result}.
	 */
	public abstract void setRecipientsIds(List<String> recipientsIds);
	public abstract Perception getPerception();
	public abstract void setPerception(Perception perception);
}