package uk.ac.rhul.cs.dice.gawl.interfaces.common;

/**
 * The interface for appearances.<br/><br/>
 * 
 * Known implementations: {@link BodyAppearance}, {@link SimpleEnvironmentAppearance}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Kostas Stathis
 *
 */
public interface Appearance {
	public String represent();
	public String getName();
	public void setName(String name);
}