package uk.ac.rhul.cs.dice.starworlds.entities;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.EnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.utils.Identifiable;

/**
 * The interface for classes capable of performing an
 * {@link EnvironmentalAction}.<br/>
 * <br>
 * 
 * Known implementations: {@link ActiveBody}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public interface Actor extends Runnable, Identifiable {

}