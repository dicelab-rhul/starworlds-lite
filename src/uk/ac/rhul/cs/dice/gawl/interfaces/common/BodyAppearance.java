package uk.ac.rhul.cs.dice.gawl.interfaces.common;

import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Body;

/**
 * The {@link Appearance} of a {@link Body}.<br/><br/>
 * 
 * Known direct subclasses: {@link PassiveBodyAppearance}, {@link AbstractAgentAppearance}, {@link DependentBodyAppearance}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Kostas Stathis
 *
 */
public abstract class BodyAppearance implements Appearance {
	private String bodyName;
	private double[] dimensions;
	
	public BodyAppearance(String name, double[] dimensions) {
		this.bodyName = name;
		this.dimensions = dimensions;
	}

	public String getBodyName() {
		return this.bodyName;
	}

	public void setBodyName(String bodyName) {
		this.bodyName = bodyName;
	}

	public double[] getDimensions() {
		return this.dimensions;
	}

	public void setDimensions(double[] dimensions) {
		this.dimensions = dimensions;
	}
}