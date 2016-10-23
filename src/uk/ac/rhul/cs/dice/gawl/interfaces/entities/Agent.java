package uk.ac.rhul.cs.dice.gawl.interfaces.entities;

import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgentBrain;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgentMind;

/**
 * The interface for agents.<br/><br/>
 * 
 * Known implementations: {@link AbstractAgent}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public interface Agent extends Actor {
	public abstract AbstractAgentBrain getBrain();
	public abstract void setBrain(AbstractAgentBrain brain);
	public abstract AbstractAgentMind getMind();
	public abstract void setMind(AbstractAgentMind mind);
	public abstract int getPerceptionRange();
	public abstract boolean canSeeBehind();
}