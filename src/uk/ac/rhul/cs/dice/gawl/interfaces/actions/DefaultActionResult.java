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
	private String recipientId;
	
	/**
	 * Constructor with an {@link ActionResult}, an {@link Exception} and a {@link String}. It is used when an
	 * action has failed.
	 * 
	 * @param result : an {@link ActionResult} expressing the result of an action.
	 * @param failureReason : an {@link Exception} (if any) possibly explaining the reason (if any)
	 * of the failure (if the action failed).
	 * @param recipientId : the {@link String} id of the object which will receive this {@link Result}.
	 */
	public DefaultActionResult(ActionResult result, Exception failureReason, String recipientId) {
		this.actionResult = result;
		this.failureReason = failureReason;
		this.recipientId = recipientId;
	}
	
	/**
	 * Constructor with an {@link ActionResult} and a {@link String}.
	 * 
	 * @param result : an {@link ActionResult} expressing the result of an action.
	 * @param recipientId : the {@link String} id of the object which will receive this {@link Result}.
	 */
	public DefaultActionResult(ActionResult result, String recipientId) {
		this.actionResult = result;
		this.failureReason = null;
		this.recipientId = recipientId;
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
	public String getRecipientId() {
		return this.recipientId;
	}
	
	@Override
	public void setRecipientId(String recipientId) {
		this.recipientId = recipientId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actionResult == null) ? 0 : actionResult.hashCode());
		result = prime * result + ((failureReason == null) ? 0 : failureReason.hashCode());
		result = prime * result + ((recipientId == null) ? 0 : recipientId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefaultActionResult other = (DefaultActionResult) obj;
		if (actionResult != other.actionResult)
			return false;
		if (failureReason == null) {
			if (other.failureReason != null)
				return false;
		} else if (!failureReason.equals(other.failureReason))
			return false;
		if (recipientId == null) {
			if (other.recipientId != null)
				return false;
		} else if (!recipientId.equals(other.recipientId))
			return false;
		return true;
	}
}