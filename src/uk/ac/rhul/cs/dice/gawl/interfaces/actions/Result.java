package uk.ac.rhul.cs.dice.gawl.interfaces.actions;

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
	 * Returns the {@link ActionResult} wrapped by this object.
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
	 */
	public Exception getFailureReason();
	
	/**
	 * Returns the {@link String} id of the object which will receive this {@link Result}.
	 * 
	 * @return the {@link String} id of the object which will receive this {@link Result}.
	 */
	public String getRecipientId();
	
	/**
	 * Sets the recipientId for this {@link Result}.
	 * 
	 * @param recipientId : the recipientId for this {@link Result}.
	 */
	public void setRecipientId(String recipientId);
}