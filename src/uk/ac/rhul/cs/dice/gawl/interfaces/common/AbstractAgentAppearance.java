package uk.ac.rhul.cs.dice.gawl.interfaces.common;

import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgent;

/**
 * The appearance of an {@link AbstractAgent}, subclass of {@link BodyAppearance}.<br/><br/>
 * 
 * Known direct subclasses: none.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Kostas Stathis
 *
 */
public abstract class AbstractAgentAppearance extends BodyAppearance {

	/**
	 * Constructor with a {@link String} name and a pair of {@link Double}.
	 * 
	 * @param name : the name of the {@link AbstractAgent}.
	 * @param dimensions : the size of the {@link AbstractAgent} represented by a {@link Double} array.
	 */
	public AbstractAgentAppearance(String name, Double[] dimensions) {
		super(name, dimensions);
	}
}