package uk.ac.rhul.cs.dice.gawl.interfaces.common;

import java.util.ArrayList;
import java.util.List;

import uk.ac.rhul.cs.dice.gawl.interfaces.environment.ComplexEnvironment;

/**
 * The {@link Appearance} of a {@link ComplexEnvironment}, extending {@link SimpleEnvironmentAppearance}.<br/><br/>
 * 
 * Known direct subclases: {@link UniverseAppearance}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Kostas Stathis
 *
 */
public abstract class ComplexEnvironmentAppearance extends SimpleEnvironmentAppearance {
	private List<Appearance> subAppearances;
	
	public ComplexEnvironmentAppearance(String name) {
		super(name);
		this.subAppearances = new ArrayList<>();
	}
	
	public void addSubEnvironmentAppearance(SimpleEnvironmentAppearance appearance) {
		this.subAppearances.add(appearance);
	}
	
	public void addSubEnvironmentAppearance(ComplexEnvironmentAppearance appearance) {
		this.subAppearances.add(appearance);
	}
	
	public List<Appearance> getSubAppearances() {
		return this.subAppearances;
	}
	
	public Appearance getSpecificSubAppearance(int index) {
		return this.subAppearances.get(index);
	}
}