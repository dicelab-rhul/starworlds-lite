package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.EnvironmentalAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Result;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;
import uk.ac.rhul.cs.dice.gawl.interfaces.perception.Perception;

/**
 * A {@link Mind} implementation which extends {@link CustomObservable}.<br/><br/>
 * 
 * Known direct subclasses: {@link AutonomousAgentMind}, {@link AvatarMind}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class AbstractAgentMind<P extends Perception> extends CustomObservable implements Mind<P> {
	private List<Class<? extends EnvironmentalAction<P>>> availableActionsForThisCycle;
	private List<Class<? extends EnvironmentalAction<P>>> mindActions;
	private Random rng;
	private String bodyId;
	private EnvironmentalAction<P> nextAction;
	private Result<P> lastActionResult;
	
	public AbstractAgentMind(Random rng, String bodyId) {
		this.rng = rng;
		this.bodyId = bodyId;
		this.availableActionsForThisCycle = new ArrayList<>();
		this.mindActions = new ArrayList<>();
	}
	
	public AbstractAgentMind(String bodyId) {
		this.rng = new Random(System.currentTimeMillis());
		this.bodyId = bodyId;
		this.availableActionsForThisCycle = new ArrayList<>();
		this.mindActions = new ArrayList<>();
	}
	
	@Override
	public List<Class<? extends EnvironmentalAction<P>>> getAvailableActionsForThisCycle() {
		return this.availableActionsForThisCycle;
	}

	@Override
	public void addAvailableActionForThisCycle(Class<? extends EnvironmentalAction<P>> availableActionForThisCycle) {
		if(this.availableActionsForThisCycle == null) {
			this.availableActionsForThisCycle = new ArrayList<>();
		}
		
		this.availableActionsForThisCycle.add(availableActionForThisCycle);
	}
	
	@Override
	public void loadAvailableActionsForThisCycle(List<Class<? extends EnvironmentalAction<P>>> availableActionsForThisCycle) {
		this.availableActionsForThisCycle = availableActionsForThisCycle;
	}
	
	@Override
	public void loadAvailableActionsForThisMind(List<Class<? extends EnvironmentalAction<P>>> mindActions) {
		this.mindActions = mindActions;
	}
	
	@Override
	public List<Class<? extends EnvironmentalAction<P>>> getAvailableActionsForThisMind() {
		return this.mindActions;
	}
	
	@Override
	public Class<? extends EnvironmentalAction<P>> decideActionPrototypeRandomly() {
		int availableActionsSize = this.availableActionsForThisCycle.size();
		
		if(availableActionsSize == 0) {
			return null;
		}
		else {
			int randomIndex = this.rng.nextInt(availableActionsSize);
			
			return this.availableActionsForThisCycle.get(randomIndex);
		}
	}
	
	@Override
	public Random getRNG() {
		return this.rng;
	}
	
	@Override
	public String getBodyId() {
		return this.bodyId;
	}
	
	@Override
	public void setBodyId(Object bodyId) {
		this.bodyId = bodyId.toString();
	}
	
	@Override
	public EnvironmentalAction<P> getNextAction() {
		return this.nextAction;
	}
	
	@Override
	public void setNextActionForExecution(EnvironmentalAction<P> action) {
		this.nextAction = action;	
	}
	
	@Override
	public Result<P> getLastActionResult() {
		return this.lastActionResult;
	}
	
	@Override
	public void setLastActionResult(Result<P> result) {
		this.lastActionResult = result;
	}
	
	@Override
	public P getPerception() {
		if(this.lastActionResult == null) {
			return null;
		}
		else {
			return this.lastActionResult.getPerception();
		}
	}
}