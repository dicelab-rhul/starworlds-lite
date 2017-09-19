package uk.ac.rhul.cs.dice.starworlds.entities.agent.components;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.entities.Entity;

public interface Component extends Entity {

	public void attempt(AbstractEnvironmentalAction action);

}
