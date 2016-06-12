package uk.ac.rhul.cs.dice.gawl.interfaces.common;

import java.util.Map;

import uk.ac.rhul.cs.dice.gawl.interfaces.environment.SpaceCoordinates;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Universe;

/**
 * The appearance of a {@link Universe}, subclass of {@link ComplexEnvironmentAppearance}.
 * It contains a pair of {@link Double} bounds for every {@link SpaceCoordinates} type.<br/><br/>
 * 
 * Known direct subclasses: //TODO
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Kostas Stathis
 *
 */
public abstract class UniverseAppearance extends ComplexEnvironmentAppearance {
	private Map<SpaceCoordinates, Double[]> bounds;
	
	public UniverseAppearance(String name, Map<SpaceCoordinates, Double[]> bounds) {
		super(name);
		this.bounds = bounds;
	}

	public Map<SpaceCoordinates, Double[]> getBounds() {
		return bounds;
	}
	
	public Double[] getBounds(SpaceCoordinates coord) {
		return bounds.get(coord);
	}
}