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
}