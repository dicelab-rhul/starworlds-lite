package uk.ac.rhul.cs.dice.starworlds.entities;

import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgentMind;

/**
 * The interface for agents.<br/>
 * <br/>
 * 
 * Known implementations: {@link AbstractAgent}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public interface Agent extends Actor {

	public  AbstractAgentMind getMind();

}