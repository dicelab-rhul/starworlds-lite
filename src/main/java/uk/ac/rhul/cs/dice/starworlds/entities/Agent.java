package uk.ac.rhul.cs.dice.starworlds.entities;

import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractAutonomousAgent;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractMind;

/**
 * The interface for agents.<br/>
 * <br/>
 * 
 * Known implementations: {@link AbstractAutonomousAgent}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public interface Agent<D, E> extends Actor {

	public AbstractMind<D, E> getMind();

}