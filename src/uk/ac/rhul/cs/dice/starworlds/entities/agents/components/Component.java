package uk.ac.rhul.cs.dice.starworlds.entities.agents.components;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;

public interface Component {

	public void attempt(AbstractEnvironmentalAction action);

}
