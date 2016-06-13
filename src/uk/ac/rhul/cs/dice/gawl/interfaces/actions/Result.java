package uk.ac.rhul.cs.dice.gawl.interfaces.actions;

/**
 * The interface for generic action results.<br/><br/>
 * 
 * Known implementations: {@link AbstractActionResult}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Kostas Stathis
 *
 */
public interface Result {
	public ActionResult getActionResult();
	public void changeActionResult(ActionResult newResult);
	public Exception getFailureReason();
}