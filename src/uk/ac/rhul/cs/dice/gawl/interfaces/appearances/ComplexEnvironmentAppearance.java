package uk.ac.rhul.cs.dice.gawl.interfaces.appearances;

import java.util.ArrayList;
import java.util.List;

import uk.ac.rhul.cs.dice.gawl.interfaces.environment.ComplexEnvironment;

/**
 * The {@link Appearance} of a {@link ComplexEnvironment}, extending {@link SimpleEnvironmentAppearance}.<br/><br/>
 * 
 * Known direct subclases: {@link UniverseAppearance}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class ComplexEnvironmentAppearance extends SimpleEnvironmentAppearance {
	private List<Appearance> subAppearances;
	
	/**
	 * Constructor with a {@link String} name.
	 * 
	 * @param name : the {@link String} name of the {@link ComplexEnvironment}.
	 */
	public ComplexEnvironmentAppearance(String name) {
		super(name);
		this.subAppearances = new ArrayList<>();
	}
	
	/**
	 * Adds a {@link SimpleEnvironmentAppearance} to the list of sub-environments appearances.
	 * 
	 * @param appearance : the sub-environment {@link SimpleEnvironmentAppearance}.
	 */
	public void addSubEnvironmentAppearance(SimpleEnvironmentAppearance appearance) {
		this.subAppearances.add(appearance);
	}
	
	/**
	 * Adds a {@link ComplexEnvironmentAppearance} to the list of sub-environments appearances.
	 * 
	 * @param appearance : the sub-environment {@link ComplexEnvironmentAppearance}.
	 */
	public void addSubEnvironmentAppearance(ComplexEnvironmentAppearance appearance) {
		this.subAppearances.add(appearance);
	}
	
	/**
	 * Returns the {@link List} of sub-environments {@link Appearance} instances.
	 * 
	 * @return a {@link List} of sub-environments {@link Appearance} instances.
	 */
	public List<Appearance> getSubAppearances() {
		return this.subAppearances;
	}
	
	/**
	 * Returns the {@link Appearance} of a specific sub-environment specified by the <code>index</code> parameter.
	 * 
	 * @param index : the index of the sub-environment whose {@link Appearance} is to e returned.
	 * @return the {@link Appearance} of the sub-environment specified by <code>index</code>
	 */
	public Appearance getSpecificSubAppearance(int index) {
		return this.subAppearances.get(index);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((subAppearances == null) ? 0 : subAppearances.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComplexEnvironmentAppearance other = (ComplexEnvironmentAppearance) obj;
		if (subAppearances == null) {
			if (other.subAppearances != null)
				return false;
		} else if (!subAppearances.equals(other.subAppearances))
			return false;
		return true;
	}
}