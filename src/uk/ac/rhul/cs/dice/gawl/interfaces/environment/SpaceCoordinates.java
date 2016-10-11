package uk.ac.rhul.cs.dice.gawl.interfaces.environment;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import uk.ac.rhul.cs.dice.gawl.interfaces.environment.locations.Location;

/**
 * An enumeration of the possible relative positions of a {@link Location} with respect to another one.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public enum SpaceCoordinates {
	NORTH, SOUTH, WEST, EAST, UP, CENTER, DOWN;
	
	private static Map<String, SpaceCoordinates> lookup = initLookupMap();
	private static Map<SpaceCoordinates, String> reverseLookup = initReverseLookupMap();
	
	public static SpaceCoordinates fromString(String candidate) {
		SpaceCoordinates toReturn = SpaceCoordinates.lookup.get(candidate);
		
		if(toReturn != null) {
			return toReturn;
		}
		else {
			throw new IllegalArgumentException("Bad SpaceCoordinates representation: " + candidate + ".");
		}
	}
	
	@Override
	public String toString() {
		String toReturn = SpaceCoordinates.reverseLookup.get(this);
		
		if(toReturn != null) {
			return toReturn;
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	private static Map<SpaceCoordinates, String> initReverseLookupMap() {
		Map<SpaceCoordinates, String> toReturn = new EnumMap<>(SpaceCoordinates.class);
		
		toReturn.put(SpaceCoordinates.NORTH, "NORTH");
		toReturn.put(SpaceCoordinates.SOUTH, "SOUTH");
		toReturn.put(SpaceCoordinates.WEST, "WEST");
		toReturn.put(SpaceCoordinates.EAST, "EAST");
		toReturn.put(SpaceCoordinates.UP, "UP");
		toReturn.put(SpaceCoordinates.CENTER, "CENTER");
		toReturn.put(SpaceCoordinates.DOWN, "DOWN");
		
		return toReturn;
	}

	private static Map<String, SpaceCoordinates> initLookupMap() {
		Map<String, SpaceCoordinates> toReturn = new HashMap<>();
		
		toReturn.put("NORTH", SpaceCoordinates.NORTH);
		toReturn.put("SOUTH", SpaceCoordinates.SOUTH);
		toReturn.put("WEST", SpaceCoordinates.WEST);
		toReturn.put("EAST", SpaceCoordinates.EAST);
		toReturn.put("UP", SpaceCoordinates.UP);
		toReturn.put("CENTER", SpaceCoordinates.CENTER);
		toReturn.put("DOWN", SpaceCoordinates.DOWN);
		
		return toReturn;
	}
}