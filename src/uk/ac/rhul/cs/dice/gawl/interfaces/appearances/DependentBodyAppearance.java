package uk.ac.rhul.cs.dice.gawl.interfaces.appearances;

import uk.ac.rhul.cs.dice.gawl.interfaces.entities.DependentBody;

/**
 * A subclass of {@link BodyAppearance} contained in {@link DependentBody} objects.<br/><br/>
 * 
 * Known direct subclasses: none.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class DependentBodyAppearance extends BodyAppearance {

	/**
	 * Constructor with a {@link String} name and a pair of {@link Double}.
	 * 
	 * @param name : the name of the {@link DependentBody}.
	 * @param dimensions : the size of the {@link DependentBody} represented by a {@link Double} array.
	 */
	public DependentBodyAppearance(String name, Double[] dimensions) {
		super(name, dimensions);
	}
}