package uk.ac.rhul.cs.dice.gawl.interfaces.appearances;

import java.util.Map;

import uk.ac.rhul.cs.dice.gawl.interfaces.environment.SpaceCoordinates;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Universe;

/**
 * The appearance of a {@link Universe}, subclass of {@link ComplexEnvironmentAppearance}.
 * It contains a pair of {@link Double} bounds for every {@link SpaceCoordinates} type.<br/><br/>
 * 
 * Known direct subclasses: none.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class UniverseAppearance extends ComplexEnvironmentAppearance {
	private Map<SpaceCoordinates, Double[]> bounds;
	
	/**
	 * Constructor with a {@link String} name and a {@link Map} with a pair of {@link Double} bounds for
	 * every {@link SpaceCoordinates} instance in the {@link Universe}.
	 * 
	 * @param name : the {@link String} name of the {@link Universe}.
	 * @param bounds : the {@link Map} from {@link SpaceCoordinates} to {@link Double} array.
	 */
	public UniverseAppearance(String name, Map<SpaceCoordinates, Double[]> bounds) {
		super(name);
		this.bounds = bounds;
	}

	/**
	 * Returns the bounds of the {@link Universe}.
	 * 
	 * @return a {@link Map} with a pair of {@link Double} bounds for
	 * every {@link SpaceCoordinates} instance in the {@link Universe}.
	 */
	public Map<SpaceCoordinates, Double[]> getBounds() {
		return bounds;
	}
	
	/**
	 * Returns the bounds for a particular {@link SpaceCoordinates} instance.
	 * 
	 * @param coord : the {@link SpaceCoordinates} instance which is the key for the bounds {@link Map}.
	 * @return the bounds for a particular {@link SpaceCoordinates} instance.
	 */
	public Double[] getBounds(SpaceCoordinates coord) {
		return bounds.get(coord);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((bounds == null) ? 0 : bounds.hashCode());
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
		UniverseAppearance other = (UniverseAppearance) obj;
		if (bounds == null) {
			if (other.bounds != null)
				return false;
		} else if (!bounds.equals(other.bounds))
			return false;
		return true;
	}
}