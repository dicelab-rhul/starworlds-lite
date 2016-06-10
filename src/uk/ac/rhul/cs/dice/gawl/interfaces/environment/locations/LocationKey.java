package uk.ac.rhul.cs.dice.gawl.interfaces.environment.locations;

import uk.ac.rhul.cs.dice.gawl.interfaces.environment.AbstractCoordinates;

/**
 * The interface for location keys to use as keys in a {@link java.util.Map Map} to get a {@link Location}.<br/><br/>
 * 
 * Known classes implementing this: {@link AbstractCoordinates}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Kostas Stathis
 *
 */
public interface LocationKey extends Comparable<LocationKey> {

}