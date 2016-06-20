package uk.ac.rhul.cs.dice.gawl.interfaces.environment;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import uk.ac.rhul.cs.dice.gawl.interfaces.environment.locations.Location;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.locations.LocationKey;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObserver;

import java.util.Set;

/**
 * The generic class for spaces related to an {@link AbstractEnvironment}. It extends {@link CustomObservable}, it is an implementation of
 * {@link Space} and {@link CustomObserver}, and it maps a {@link LocationKey} to a {@link Location} through a {@link Map}.<br/><br/>
 * 
 * Known direct subclasses: none.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class EnvironmentalSpace extends CustomObservable implements Space, CustomObserver {
	private Map<LocationKey, Location> grid;
	
	/**
	 * The void constructor. It initialises the internal {@link HashMap}.
	 */
	public EnvironmentalSpace(){
		this.grid = new HashMap<>();
	}
	
	/**
	 * A constructor with a {@link Map} linking {@link LocationKey} keys to {@link Location} values.
	 * 
	 * @param grid : a {@link Map} linking {@link LocationKey} keys to {@link Location} values.
	 */
	public EnvironmentalSpace(Map<LocationKey, Location> grid) {
		this.grid = grid;
	}
	
	/**
	 * A constructor with a first pair {@link LocationKey} - {@link Location}.
	 * 
	 * @param locationKey : a {@link LocationKey} instance.
	 * @param location : a {@link Location} instance.
	 */
	public EnvironmentalSpace(LocationKey locationKey, Location location) {
		this.grid = new HashMap<>();
		this.grid.put(locationKey, location);
	}
	
	/**
	 * Returns the internal {@link HashMap}. Try to use this method only if there is no other wrapper
	 * method for what you need to do.
	 * 
	 * @return the internal {@link Map} from {@link LocationKey} keys to {@link Location} values.
	 */
	public Map<LocationKey, Location> getGrid() {
		return this.grid;
	}
	
	/**
	 * Adds a {@link Location} to the internal {@link HashMap}.
	 * 
	 * @param locationKey : the {@link LocationKey} key.
	 * @param location : the {@link Location} value.
	 */
	public void addLocation(LocationKey locationKey, Location location) {
		this.grid.put(locationKey, location);
	}
	
	/**
	 * Removes the {@link Location} pointed by <code>locationKey</code>.
	 * 
	 * @param locationKey : the {@link LocationKey} key for the {@link Location} value to be deleted.
	 */
	public void removeLocation(LocationKey locationKey) {
		this.grid.remove(locationKey);
	}
	
	/**
	 * Removes and returns the {@link Location} pointed by <code>locationKey</code>.
	 * 
	 * @param locationKey : the {@link LocationKey} key for the {@link Location} value to be popped.
	 * @return the {@link Location} value pointed by <code>locationKey</code>.
	 */
	public Location popLocation(LocationKey locationKey) {
		return this.grid.remove(locationKey);
	}
	
	/**
	 * Returns the {@link Location} pointed by <code>locationKey</code>.
	 * 
	 * @param locationKey : the {@link LocationKey} key for the {@link Location} value to be returned.
	 * @return the {@link Location} value pointed by <code>locationKey</code>.
	 */
	public Location getLocation(LocationKey locationKey) {
		return this.grid.get(locationKey);
	}
	
	/**
	 * Checks whether the internal {@link HashMap} contains a specific {@link LocationKey}.
	 * 
	 * @param locationKey the {@link LocationKey} instance to be looked for.
	 * @return <code>true</code> if the key is found, <code>false</code> otherwise.
	 */
	public boolean containsKey(LocationKey locationKey) {
		return this.grid.containsKey(locationKey);
	}
	
	/**
	 * Checks whether the internal {@link HashMap} contains a specific {@link Location}.
	 * 
	 * @param location the {@link Location} instance to be looked for.
	 * @return <code>true</code> if the {@link Location} instance is found, <code>false</code> otherwise.
	 */
	public boolean containsValue(Location location) {
		return this.grid.containsValue(location);
	}
	
	/**
	 * Returns the keyset of the internal {@link HashMap}.
	 * 
	 * @return a {@link Set} of {@link LocationKey} elements.
	 */
	public Set<LocationKey> getKeys() {
		return this.grid.keySet();
	}
	
	/**
	 * Returns the values of the internal {@link HashMap}.
	 * 
	 * @return a {@link Collection} of {@link Location} elements.
	 */
	public Collection<Location> getLocations() {
		return this.grid.values();
	}
	
	/**
	 * Returns the set of entries {@link LocationKey} - {@link Location} of the internal {@link HashMap}.
	 * 
	 * @return a {@link Set} of {@link Entry} elements.
	 */
	public Set<Entry<LocationKey, Location>> getEntries() {
		return this.grid.entrySet();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((grid == null) ? 0 : grid.hashCode());
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
		EnvironmentalSpace other = (EnvironmentalSpace) obj;
		if (grid == null) {
			if (other.grid != null)
				return false;
		} else if (!grid.equals(other.grid))
			return false;
		return true;
	}
}