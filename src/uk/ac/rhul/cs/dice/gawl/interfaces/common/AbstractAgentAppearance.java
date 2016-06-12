package uk.ac.rhul.cs.dice.gawl.interfaces.common;

import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgent;

/**
 * The appearance of an {@link AbstractAgent}, subclass of {@link BodyAppearance}.<br/><br/>
 * 
 * Known direct subclasses: //TODO
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Kostas Stathis
 *
 */
public abstract class AbstractAgentAppearance extends BodyAppearance {

	public AbstractAgentAppearance(String name, double[] dimensions) {
		super(name, dimensions);
	}
}