package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.components;

import java.util.Set;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.ActiveBody;

/**
 * The interface for actuators. It extends {@link CustomObserver}.<br/>
 * <br/>
 * 
 * Known implementations: {@link AbstractActuator}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public interface Actuator {

	public String getId();

	public ActiveBody getBody();

	public void setBody(ActiveBody body);

	public void performAction(AbstractEnvironmentalAction action);

}