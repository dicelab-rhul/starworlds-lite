package uk.ac.rhul.cs.dice.gawl.interfaces.appearances;

import uk.ac.rhul.cs.dice.gawl.interfaces.environment.SimpleEnvironment;

/**
 * The {@link Appearance} of a {@link SimpleEnvironment}.<br/><br/>
 * 
 * Known direct subclasses: {@link ComplexEnvironmentAppearance}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class SimpleEnvironmentAppearance implements Appearance {
	private String name;
	
	/**
	 * Constructor with a {@link String} name.
	 * 
	 * @param name : the {@link String} name of the {@link SimpleEnvironment}.
	 */
	public SimpleEnvironmentAppearance(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		SimpleEnvironmentAppearance other = (SimpleEnvironmentAppearance) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}