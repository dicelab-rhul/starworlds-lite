package uk.ac.rhul.cs.dice.gawl.interfaces.appearances;

import java.util.Arrays;

import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Body;

/**
 * The {@link Appearance} of a {@link Body}.<br/><br/>
 * 
 * Known direct subclasses: {@link PassiveBodyAppearance}, {@link AbstractAgentAppearance}, {@link DependentBodyAppearance}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bodyName == null) ? 0 : bodyName.hashCode());
		result = prime * result + Arrays.hashCode(dimensions);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BodyAppearance other = (BodyAppearance) obj;
		if (bodyName == null) {
			if (other.bodyName != null)
				return false;
		} else if (!bodyName.equals(other.bodyName))
			return false;
		if (!Arrays.equals(dimensions, other.dimensions))
			return false;
		return true;
	}
}