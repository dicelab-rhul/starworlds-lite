package uk.ac.rhul.cs.dice.starworlds.entities.agents.components;

import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SensingAction;
import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;

/**
 * The interface for sensors. It extends {@link CustomObserver}.<br/>
 * <br/>
 * 
 * Known implementations: {@link AbstractSensor}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public interface Sensor extends Component {

	public String getId();

	public ActiveBody getBody();

	public void setBody(ActiveBody body);

	public void attemptActivePerceive(SensingAction action);

	public Set<Perception<?>> getPerceptions();

	public void notify(Perception<?> perception);

}