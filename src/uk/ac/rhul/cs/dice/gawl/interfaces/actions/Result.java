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
@FunctionalInterface
public interface Result {
	public ActionResult getActionResult();
}