package uk.ac.rhul.cs.dice.gawl.interfaces.actions;

/**
 * A {@link Result} implementation containing an {@link DefaultActionResult}.<br/><br/>
 * 
 * Known direct subclasses: none.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Kostas Stathis
 *
 */
public class DefaultActionResult implements Result {
	private ActionResult actionResult;
	private Exception failureReason;
	
	public DefaultActionResult(ActionResult result, Exception failureReason) {
		this.actionResult = result;
		this.failureReason = failureReason;
	}
	
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