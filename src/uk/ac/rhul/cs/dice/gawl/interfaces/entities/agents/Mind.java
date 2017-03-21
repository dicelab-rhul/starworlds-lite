package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents;

import java.util.List;
import java.util.Random;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.EnvironmentalAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Result;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObserver;
import uk.ac.rhul.cs.dice.gawl.interfaces.perception.Perception;

/**
 * The interface for minds. it extends {@link CustomObserver}.<br/>
 * <br/>
 * 
 * Known implementations: {@link AbstractAgentMind}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public interface Mind extends CustomObserver {
    /**
     *
     * The perceive() routine. The behavior strictly depends on the implementation.
     * 
     * @param perceptionWrapper some parameter.
     * 
     */
    public abstract void perceive(Object perceptionWrapper);

    /**
     * 
     * The decide() routine. At the end of it the next action is returned. This method needs to be implemented by subclasses.
     * 
     * @param parameters an array of optional parameters.
     * @return the next {@link EnvironmentalAction} to execute.
     * 
     */
    public abstract EnvironmentalAction decide(Object... parameters);

    /**
     * 
     * The execute() routine. The behavior strictly depends on the implementation.
     * 
     * @param action
     * 
     */
    public abstract void execute(EnvironmentalAction action);

    /**
     * 
     * Returns the available actions for execution for this cycle.
     * 
     * @return the available actions for execution for this cycle.
     * 
     */
    public abstract List<Class<? extends EnvironmentalAction>> getAvailableActionsForThisCycle();

    /**
     * 
     * Adds an  {@link EnvironmentalAction} to the {@link List} of available actions.
     * 
     * @param availableActionForThisCycle the {@link Class} of the {@link EnvironmentalAction} to add.
     * 
     */
    public abstract void addAvailableActionForThisCycle(Class<? extends EnvironmentalAction> availableActionForThisCycle);

    /**
     * 
     * Loads the available actions for this cycle.
     * 
     * @param availableActionsForThisCycle the available actions for this cycle.
     * 
     */
    public abstract void loadAvailableActionsForThisCycle(List<Class<? extends EnvironmentalAction>> availableActionsForThisCycle);

    /**
     * 
     * Loads and stores the complete list of actions which this mind can execute (in general, non on a specific cycle).
     * 
     * @param mindActions the actions to load.
     * 
     */
    public abstract void loadAvailableActionsForThisMindFromArbitraryParameters(Object... mindActions);

    /**
     * 
     * Loads and stores a particular action which this mind can execute (in general, non on a specific cycle).
     * 
     * @param mindAction the action to load.
     * 
     */
    public abstract void loadAvailableActionForThisMind(Class<? extends EnvironmentalAction> mindAction);

    /**
     * 
     * Returns the {@link List} of {@link EnvironmentalAction} prototypes that this mind can execute (in general, non on a specific cycle).
     * 
     * @return the {@link List} of {@link EnvironmentalAction} prototypes that this mind can execute.
     * 
     */
    public abstract List<Class<? extends EnvironmentalAction>> getAvailableActionsForThisMind();

    /**
     * 
     * Returns a random {@link EnvironmentalAction} prototype.
     * 
     * @return a random {@link EnvironmentalAction} prototype.
     * 
     */
    public abstract Class<? extends EnvironmentalAction> decideActionPrototypeRandomly();

    /**
     * 
     * Returns a random {@link EnvironmentalAction}.
     * 
     * @return a random {@link EnvironmentalAction}.
     * 
     */
    public abstract EnvironmentalAction decideActionRandomly();

    /**
     * 
     * Returns this mind's random numbers generator.
     * 
     * @return this mind's random numbers generator.
     * 
     */
    public abstract Random getRNG();

    /**
     * 
     * Returns the body's id.
     * 
     * @return the body's id.
     * 
     */
    public abstract Object getBodyId();

    /**
     * 
     * Stores the body's id.
     * 
     * @param bodyId the body's id's to store.
     * 
     */
    public abstract void setBodyId(Object bodyId);

    /**
     * 
     * Checks whether the last action succeeded or not.
     * 
     * @return true if the last action succeeded or not.
     * 
     */
    public abstract boolean lastActionSucceeded();

    /**
     * 
     * Checks whether the last action failed during execution or not.
     * 
     * @return true if the last action failed during execution, false otherwise.
     * 
     */
    public abstract boolean lastActionFailed();

    /**
     * 
     * Checks whether the last action was labeled as impossible to perform.
     * 
     * @return true if the last action was labeled as impossible to perform, false otherwise.
     * 
     */
    public abstract boolean wasLastActionImpossible();

    /**
     * 
     * Returns the perception range.
     * 
     * @return the perception range.
     * 
     */
    public abstract int getPerceptionRange();

    /**
     * 
     * Returns whether it is possible to see behind or not.
     * 
     * @return true if it is possible to see behind, false otherwise.
     * 
     */
    public abstract boolean canSeeBehind();

    /**
     * 
     * Stores the fact that it is or it is not possible to see behind.
     * 
     * @param canSeeBehind the fact that it is or it is not possible to see behind.
     * 
     */
    public abstract void setCanSeeBehind(boolean canSeeBehind);

    /**
     * 
     * Stores the perception range.
     * 
     * @param preceptionRange the perception range.
     * 
     */
    public abstract void setPerceptionRange(int preceptionRange);

    /**
     * 
     * Returns the next action for execution.
     * 
     * @return the next {@link EnvironmentalAction} for execution.
     * 
     */
    public abstract EnvironmentalAction getNextAction();

    /**
     * 
     * Stores the next action for execution.
     * 
     * @param action the next {@link EnvironmentalAction} selected for execution to store.
     * 
     */
    public abstract void setNextActionForExecution(EnvironmentalAction action);

    /**
     * 
     * Returns the last action {@link Result}.
     * 
     * @return the last action {@link Result}.
     * 
     */
    public abstract Result getLastActionResult();

    /**
     * 
     * Stores the last action {@link Result}.
     * 
     * @param result the last action {@link Result} to store.
     * 
     */
    public abstract void setLastActionResult(Result result);

    /**
     * 
     * Returns the most recent {@link Perception}. It can be null.
     * 
     * @return the most recent {@link Perception}.
     * 
     */
    public abstract Perception getPerception();

    /**
     * 
     * Returns a {@link List} of wrappers for the communication received during the last cycle.
     * 
     * @return a {@link List} of wrappers for the communication received during the last cycle.
     * 
     */
    public abstract List<Result> getReceivedCommunications();

    /**
     * 
     * Pushes a received communication wrapper to a {@link List}.
     * 
     * @param communicationResult a received communication wrapper.
     * 
     */
    public abstract void addReceivedCommunicationToList(Result communicationResult);

    /**
     * 
     * Clears the received communications {@link List}.
     * 
     */
    public abstract void clearReceivedCommunications();
}