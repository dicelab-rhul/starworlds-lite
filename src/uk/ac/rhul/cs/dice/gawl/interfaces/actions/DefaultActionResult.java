package uk.ac.rhul.cs.dice.gawl.interfaces.actions;

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
	private ActionResult actionResult;
	private Exception failureReason;
	
	/**
	 * Constructor with an {@link ActionResult} and an {@link Exception}. It is used when an
	 * action has failed.
	 * 
	 * @param result : an {@link ActionResult} expressing the result of an action.
	 * @param failureReason : an {@link Exception} (if any) possibly explaining the reason (if any)
	 * of the failure (if the action failed).
	 */
	public DefaultActionResult(ActionResult result, Exception failureReason) {
		this.actionResult = result;
		this.failureReason = failureReason;
	}
	
	/**
	 * Constructor with an {@link ActionResult}.
	 * 
	 * @param result : an {@link ActionResult} expressing the result of an action.
	 */
	public DefaultActionResult(ActionResult result) {
		this.actionResult = result;
		this.failureReason = null;
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
}