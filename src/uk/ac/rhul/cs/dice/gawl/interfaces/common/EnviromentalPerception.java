package uk.ac.rhul.cs.dice.gawl.interfaces.common;

import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractSensor;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.EnvironmentalSpace;

/**
 * The class representing a {@link Perception} of an environment as seen by subclasses of {@link AbstractSensor}. It is a subclass
 * of {@link EnvironmentalSpace}.<br/><br/>
 * 
 * Known direct subclasses: //TODO
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Kostas Stathis
 *
 */
public abstract class EnviromentalPerception extends EnvironmentalSpace implements Perception {

}