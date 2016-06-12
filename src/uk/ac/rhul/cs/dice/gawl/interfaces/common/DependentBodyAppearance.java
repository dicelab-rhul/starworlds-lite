package uk.ac.rhul.cs.dice.gawl.interfaces.common;

import uk.ac.rhul.cs.dice.gawl.interfaces.entities.DependentBody;

/**
 * A subclass of {@link BodyAppearance} contained in {@link DependentBody} objects.<br/><br/>
 * 
 * Known direct subclasses: //TODO
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Kostas Stathis
 *
 */
public abstract class DependentBodyAppearance extends BodyAppearance {

	public DependentBodyAppearance(String name, double[] dimensions) {
		super(name, dimensions);
	}
}