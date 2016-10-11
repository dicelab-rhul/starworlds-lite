package uk.ac.rhul.cs.dice.gawl.interfaces.actions;

import java.util.List;

/**
 * A {@link Result} implementation containing an {@link DefaultActionResult}.<br/><br/>
 * 
 * Known direct subclasses: none.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public class DefaultActionResult implements Result {
	private String actorId;
	private ActionResult actionResult;
	private Exception failureReason;
	private List<String> recipientsIds;
	
	/**
	 * Constructor with an {@link ActionResult}, an {@link Exception} and a {@link List}. It is used when an
	 * action has failed.
	 * 
	 * @param result : an {@link ActionResult} expressing the result of an action.
	 * @param failureReason : an {@link Exception} (if any) possibly explaining the reason (if any)
	 * of the failure (if the action failed).
	 * @param recipientsIds : a {@link List} of {@link String} IDs of the objects which will receive this {@link Result}.
	 */
	public DefaultActionResult(ActionResult result, Exception failureReason, List<String> recipientsIds) {
		this.actionResult = result;
		this.actorId = null;
		this.failureReason = failureReason;
		this.recipientsIds = recipientsIds;
	}
	
	/**
	 * Constructor with an {@link ActionResult}, an {@link Exception} and a {@link List}. It is used when an
	 * action has failed.
	 * 
	 * @param result : an {@link ActionResult} expressing the result of an action.
	 * @param actorId : the {@link String} actorId of the actor attempting the event this result refers to.
	 * @param failureReason : an {@link Exception} (if any) possibly explaining the reason (if any)
	 * of the failure (if the action failed).
	 * @param recipientsIds : a {@link List} of {@link String} IDs of the objects which will receive this {@link Result}.
	 */
	public DefaultActionResult(ActionResult result, String actorId, Exception failureReason, List<String> recipientsIds) {
		this.actionResult = result;
		this.actorId = actorId;
		this.failureReason = failureReason;
		this.recipientsIds = recipientsIds;
	}
	
	/**
	 * Constructor with an {@link ActionResult} and a {@link List}.
	 * 
	 * @param result : an {@link ActionResult} expressing the result of an action.
	 * @param recipientsIds : a {@link List} of {@link String} IDs of the objects which will receive this {@link Result}.
	 */
	public DefaultActionResult(ActionResult result, List<String> recipientsIds) {
		this.actionResult = result;
		this.actorId = null;
		this.failureReason = null;
		this.recipientsIds = recipientsIds;
	}
	
	/**
	 * Constructor with an {@link ActionResult} and a {@link List}.
	 * 
	 * @param result : an {@link ActionResult} expressing the result of an action.
	 * @param actorId : the {@link String} actorId of the actor attempting the event this result refers to.
	 * @param recipientsIds : a {@link List} of {@link String} IDs of the objects which will receive this {@link Result}.
	 */
	public DefaultActionResult(ActionResult result, String actorId, List<String> recipientsIds) {
		this.actionResult = result;
		this.actorId = actorId;
		this.failureReason = null;
		this.recipientsIds = recipientsIds;
	}

	@Override
	public String getActorId() {
		return this.actorId;
	}
	
	@Override
	public ActionResult getActionResult() {
		return this.actionResult;
	}

	@Override
	public void changeActionResult(ActionResult newResult) {
		this.actionResult = newResult;
	}
	
	@Override
	public Exception getFailureReason() {
		return this.failureReason;
	}

	@Override
	public List<String> getRecipientsIds() {
		return this.recipientsIds;
	}

	@Override
	public void setRecipientsIds(List<String> recipientsIds) {
		this.recipientsIds = recipientsIds;
	}
}