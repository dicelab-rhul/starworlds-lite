package uk.ac.rhul.cs.dice.starworlds.actions.environmental;

import java.io.Serializable;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.utils.Identifiable;

/**
 * The interface for all the actions that should be performed in some
 * environment by some physics.<br/>
 * <br/>
 * Implements: {@link EnvironmentalAction}. </br>
 * Known implementations: {@link AbstractEnvironmentalAction}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public interface EnvironmentalAction extends Action, Identifiable, Serializable {

}