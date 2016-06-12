package uk.ac.rhul.cs.dice.gawl.interfaces.environment;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import uk.ac.rhul.cs.dice.gawl.interfaces.environment.locations.Location;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.locations.LocationKey;

import java.util.Set;

/**
 * The generic class for spaces related to an {@link AbstractEnvironment}.
 * It is a subclass of {@link Space} and maps a {@link LocationKey} to a {@link Location} through a {@link Map}.<br/><br/>
 * 
 * Known direct subclasses: //TODO
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Kostas Stathis
 *
 */
public abstract class EnvironmentalSpace extends Space {
	private Map<LocationKey, Location> grid;
	
	public EnvironmentalSpace(){
		this.grid = new HashMap<>();
	}
	
	public EnvironmentalSpace(Map<LocationKey, Location> grid) {
		this.grid = grid;
	}
	
	public EnvironmentalSpace(LocationKey locationKey, Location location) {
		this.grid = new HashMap<>();
		this.grid.put(locationKey, location);
	}
	
	/**
	 * Try to use this method only if there is no other wrapper method for what you need to do.
	 * 
	 * @return the grid.
	 */
	public Map<LocationKey, Location> getGrid() {
		return this.grid;
	}
	
	public void addLocation(LocationKey locationKey, Location location) {
		this.grid.put(locationKey, location);
	}
	
	public void removeLocation(LocationKey locationKey) {
		this.grid.remove(locationKey);
	}
	
	public Location popLocation(LocationKey locationKey) {
		return this.grid.remove(locationKey);
	}
	
	public Location getLocation(LocationKey locationKey) {
		return this.grid.get(locationKey);
	}
	
	public boolean containsKey(LocationKey locationKey) {
		return this.grid.containsKey(locationKey);
	}
	
	public boolean containsValue(Location location) {
		return this.grid.containsValue(location);
	}
	
	public Set<LocationKey> getKeys() {
		return this.grid.keySet();
	}
	
	public Collection<Location> getLocations() {
		return this.grid.values();
	}
	
	public Set<Entry<LocationKey, Location>> getEntries() {
		return this.grid.entrySet();
	}
}