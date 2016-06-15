package uk.ac.rhul.cs.dice.gawl.interfaces.appearances;

import uk.ac.rhul.cs.dice.gawl.interfaces.entities.PassiveBody;

/**
 * The appearance of a {@link PassiveBody}, subclass of {@link BodyAppearance}.<br/><br/>
 * 
 * Known direct subclasses: none.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class PassiveBodyAppearance extends BodyAppearance {

	/**
	 * Constructor with a {@link String} name and a pair of {@link Double}.
	 * 
	 * @param name : the name of the {@link PassiveBody}.
	 * @param dimensions : the size of the {@link PassiveBody} represented by a {@link Double} array.
	 */
	public PassiveBodyAppearance(String name, Double[] dimensions) {
		super(name, dimensions);
	}
}