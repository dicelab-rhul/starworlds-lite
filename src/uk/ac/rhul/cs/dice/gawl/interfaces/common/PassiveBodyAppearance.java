package uk.ac.rhul.cs.dice.gawl.interfaces.common;

import uk.ac.rhul.cs.dice.gawl.interfaces.entities.PassiveBody;

/**
 * The appearance of a {@link PassiveBody}, subclass of {@link BodyAppearance}.<br/><br/>
 * 
 * Known direct subclasses: //TODO
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Kostas Stathis
 *
 */
public abstract class PassiveBodyAppearance extends BodyAppearance {

	public PassiveBodyAppearance(String name, double[] dimensions) {
		super(name, dimensions);
	}
}