package uk.ac.rhul.cs.dice.starworlds.entities.agents.components;

import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;

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
public interface Actuator extends Component {

	public ActiveBody getBody();

	public void setBody(ActiveBody body);

}