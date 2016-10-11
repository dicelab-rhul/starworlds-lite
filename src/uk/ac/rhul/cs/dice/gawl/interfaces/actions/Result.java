package uk.ac.rhul.cs.dice.gawl.interfaces.actions;

import java.util.List;

/**
 * The interface for generic action results.<br/><br/>
 * 
 * Known implementations: {@link DefaultActionResult}.
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
	public String getActorId();
	
	/**
	 * Returns the {@link ActionResult} wrapped by this object.
	 * 
	 * @return the {@link ActionResult} wrapped by this object.
	 */
	public ActionResult getActionResult();
	
	/**
	 * Updates the {@link ActionResult} wrapped by this object with a new one.
	 * 
	 * @param newResult : the new {@link ActionResult}.
	 */
	public void changeActionResult(ActionResult newResult);
	
	/**
	 * Returns the {@link Exception} wrapped by this object.
	 * 
	 * @return the {@link Exception} wrapped by this object.
	 */
	public Exception getFailureReason();
	
	/**
	 * Returns the {@link List} of {@link String} IDs of the objects which will receive this {@link Result}.
	 * 
	 * @return the {@link List} of {@link String} IDs of the objects which will receive this {@link Result}.
	 */
	public List<String> getRecipientsIds();
	
	/**
	 * Sets the {@link List} of {@link String} IDs of the objects which will receive this {@link Result}.
	 * 
	 * @param recipientsIds : the {@link List} of {@link String} IDs of the objects which will receive this {@link Result}.
	 */
	public void setRecipientsIds(List<String> recipientsIds);
}