package uk.ac.rhul.cs.dice.gawl.interfaces.actions;

/**
 * A generic enumeration for action results.<br/>
 * <br/>
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public enum ActionResult {
    ACTION_IMPOSSIBLE, ACTION_DONE, ACTION_FAILED;

    /**
     * Constructs an {@link ActionResult} from a {@link String} representation.
     * 
     * @param candidate:
     *            the {@link String} representation.
     * 
     * @return the {@link ActionResult} created from a {@link String}
     *         representation.
     */
    public static ActionResult fromString(String candidate) {
	switch (candidate) {
	case "ACTION_IMPOSSIBLE":
	    return ActionResult.ACTION_IMPOSSIBLE;
	case "ACTION_DONE":
	    return ActionResult.ACTION_DONE;
	case "ACTION_FAILED":
	    return ActionResult.ACTION_FAILED;
	default:
	    throw new IllegalArgumentException("Bad action result representation: " + candidate + ".");
	}
    }

    /**
     * Returns a {@link String} representation of the {@link ActionResult}.
     */
    @Override
    public String toString() {
	switch (this) {
	case ACTION_IMPOSSIBLE:
	    return "ACTION_IMPOSSIBLE";
	case ACTION_DONE:
	    return "ACTION_DONE";
	case ACTION_FAILED:
	    return "ACTION_FAILED";
	default:
	    throw new IllegalArgumentException();
	}
    }
}