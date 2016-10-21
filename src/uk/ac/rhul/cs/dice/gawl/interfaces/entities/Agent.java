package uk.ac.rhul.cs.dice.gawl.interfaces.entities;

import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgentMind;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Brain;
import uk.ac.rhul.cs.dice.gawl.interfaces.perception.Perception;

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
public interface Agent<P extends Perception> extends Actor {
	public abstract Brain getBrain();
	public abstract void setBrain(Brain brain);
	public abstract AbstractAgentMind<P> getMind();
	public abstract void setMind(AbstractAgentMind<P> mind);
}