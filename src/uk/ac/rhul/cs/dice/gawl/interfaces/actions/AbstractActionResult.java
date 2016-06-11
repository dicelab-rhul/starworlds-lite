package uk.ac.rhul.cs.dice.gawl.interfaces.actions;

/**
 * A {@link Result} implementation containing an {@link ActionResult}.<br/><br/>
 * 
 * Known direct subclasses: //TODO
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Kostas Stathis
 *
 */
public abstract class AbstractActionResult implements Result {
	private ActionResult actionResult;
	
	public AbstractActionResult(ActionResult result) {
		this.actionResult = result;
	}

	@Override
	public ActionResult getActionResult() {
		return this.actionResult;
	}

	public void setActionResult(ActionResult actionResult) {
		this.actionResult = actionResult;
	}
}