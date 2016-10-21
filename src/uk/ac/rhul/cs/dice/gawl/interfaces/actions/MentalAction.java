package uk.ac.rhul.cs.dice.gawl.interfaces.actions;

import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Mind;
import uk.ac.rhul.cs.dice.gawl.interfaces.perception.Perception;

/**
 * The interface for all the actions that should be performed inside a
 * {@link Mind}.<br/>
 * <br/>
 * Implements: {@link EnvironmentalAction}. </br>
 * Known implementations: {@link AbstractAction}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public interface MentalAction<P extends Perception> extends EnvironmentalAction<P> {

}