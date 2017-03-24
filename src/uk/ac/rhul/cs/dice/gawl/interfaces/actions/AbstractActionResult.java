package uk.ac.rhul.cs.dice.gawl.interfaces.actions;

import java.util.List;

import uk.ac.rhul.cs.dice.gawl.interfaces.perception.Perception;

/**
 * A {@link Result} implementation containing an
 * {@link AbstractActionResult}.<br/>
 * <br/>
 * 
 * Known direct subclasses: none.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class AbstractActionResult implements Result {
    private String actorId;
    private ActionResult actionResult;
    private Exception failureReason;
    private List<String> recipientsIds;
    private Perception perception;

    /**
     * Constructor with an {@link ActionResult}, an {@link Exception} and a
     * {@link List}. It is used when an action has failed.
     * 
     * @param result
     *            : an {@link ActionResult} expressing the result of an action.
     * @param failureReason
     *            : an {@link Exception} (if any) possibly explaining the reason
     *            (if any) of the failure (if the action failed).
     * @param recipientsIds
     *            : a {@link List} of {@link String} IDs of the objects which
     *            will receive this {@link Result}.
     */
    public AbstractActionResult(ActionResult result, Exception failureReason, List<String> recipientsIds) {
	this.actionResult = result;
	this.actorId = null;
	this.failureReason = failureReason;
	this.recipientsIds = recipientsIds;
	this.perception = null;
    }

    /**
     * Constructor with an {@link ActionResult}, an {@link Exception} and a
     * {@link List}. It is used when an action has failed.
     * 
     * @param result
     *            : an {@link ActionResult} expressing the result of an action.
     * @param actorId
     *            : the {@link String} actorId of the actor attempting the event
     *            this result refers to.
     * @param failureReason
     *            : an {@link Exception} (if any) possibly explaining the reason
     *            (if any) of the failure (if the action failed).
     * @param recipientsIds
     *            : a {@link List} of {@link String} IDs of the objects which
     *            will receive this {@link Result}.
     */
    public AbstractActionResult(ActionResult result, String actorId, Exception failureReason, List<String> recipientsIds) {
	this.actionResult = result;
	this.actorId = actorId;
	this.failureReason = failureReason;
	this.recipientsIds = recipientsIds;
	this.perception = null;
    }

    /**
     * Constructor with an {@link ActionResult} and a {@link List}.
     * 
     * @param result
     *            : an {@link ActionResult} expressing the result of an action.
     * @param recipientsIds
     *            : a {@link List} of {@link String} IDs of the objects which
     *            will receive this {@link Result}.
     */
    public AbstractActionResult(ActionResult result, List<String> recipientsIds, Perception perception) {
	this.actionResult = result;
	this.actorId = null;
	this.failureReason = null;
	this.recipientsIds = recipientsIds;
	this.perception = perception;
    }

    /**
     * Constructor with an {@link ActionResult} and a {@link List}.
     * 
     * @param result
     *            : an {@link ActionResult} expressing the result of an action.
     * @param actorId
     *            : the {@link String} actorId of the actor attempting the event
     *            this result refers to.
     * @param recipientsIds
     *            : a {@link List} of {@link String} IDs of the objects which
     *            will receive this {@link Result}.
     */
    public AbstractActionResult(ActionResult result, String actorId, List<String> recipientsIds, Perception perception) {
	this.actionResult = result;
	this.actorId = actorId;
	this.failureReason = null;
	this.recipientsIds = recipientsIds;
	this.perception = perception;
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

    @Override
    public Perception getPerception() {
	return this.perception;
    }

    @Override
    public void setPerception(Perception perception) {
	this.perception = perception;
    }
}