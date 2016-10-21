package uk.ac.rhul.cs.dice.gawl.interfaces.environment;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import uk.ac.rhul.cs.dice.gawl.interfaces.environment.locations.Location;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.locations.LocationKey;

/**
 * Interface for spaces. It extends {@link State}.<br/><br/>
 * 
 * Known implementations: {@link EnvironmentalSpace}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public interface Space extends State {
	public abstract Map<LocationKey, Location> getGrid();
	public abstract void addLocation(LocationKey locationKey, Location location);
	public abstract void removeLocation(LocationKey locationKey);
	public abstract Location popLocation(LocationKey locationKey);
	public abstract Location getLocation(LocationKey locationKey);
	public abstract boolean containsKey(LocationKey locationKey);
	public abstract boolean containsValue(Location location);
	public abstract Set<LocationKey> getKeys();
	public abstract Collection<Location> getLocations();
	public abstract Set<Entry<LocationKey, Location>> getEntries();
}