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
	private Double[] dimensions;
	
	/**
	 * Constructor with a {@link String} name and a pair of {@link Double}.
	 * 
	 * @param name : the name of the {@link Body}.
	 * @param dimensions : the size of the {@link Body} represented by a {@link Double} array.
	 */
	public BodyAppearance(String name, Double[] dimensions) {
		this.bodyName = name;
		this.dimensions = dimensions;
	}

	@Override
	public String getName() {
		return this.bodyName;
	}

	@Override
	public void setName(String bodyName) {
		this.bodyName = bodyName;
	}

	/**
	 * Returns the dimensions of the {@link Body}.
	 * 
	 * @return the size of the {@link Body} represented by a {@link Double} array.
	 */
	public Double[] getDimensions() {
		return this.dimensions;
	}

	/**
	 * Sets the dimensions of the {@link Body}.
	 * 
	 * @param dimensions : the size of the {@link Body} represented by a {@link Double} array.
	 */
	public void setDimensions(Double[] dimensions) {
		this.dimensions = dimensions;
	}
}