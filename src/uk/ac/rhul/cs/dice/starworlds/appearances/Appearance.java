package uk.ac.rhul.cs.dice.starworlds.appearances;

import java.io.Serializable;

/**
 * The interface for appearances. An {@link Appearance} should be
 * {@link Serializable}. <br/>
 * <br/>
 * 
 * Known implementations: {@link AbstractAppearance},
 * {@link SimpleEnvironmentAppearance}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public interface Appearance extends Serializable {
	/**
	 * Returns a {@link String} representation of the appearance.
	 * 
	 * @return a {@link String} representation of the appearance.
	 */
	public abstract String represent();

	/**
	 * Returns the {@link String} name of the object the appearance refers to.
	 * 
	 * @return the {@link String} name of the object the appearance refers to.
	 */
	public abstract String getName();

	/**
	 * Sets the {@link String} name of the object the appearance refers to.
	 * 
	 * @param name
	 *            : the {@link String} name of the object the appearance refers
	 *            to.
	 */
	public abstract void setName(String name);
}