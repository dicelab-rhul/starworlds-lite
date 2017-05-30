package uk.ac.rhul.cs.dice.starworlds.experiment.physicalagents;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.concrete.SeeingSensor;
import uk.ac.rhul.cs.dice.starworlds.environment.State;
import uk.ac.rhul.cs.dice.starworlds.perception.AbstractPerception;
import uk.ac.rhul.cs.dice.starworlds.utils.Pair;

public class BadSeeingSensor extends SeeingSensor {

	@Override
	public boolean canSense(AbstractEnvironmentalAction action,
			AbstractPerception<?> perception, State context) {
		if (super.canSense(action, perception, context)) {
			Pair<Integer, Integer> otherposition = ((PhysicalState) context)
					.getPositionOfAgent((ActiveBody) (action.getActor()));
			Pair<Integer, Integer> selfposition = ((PhysicalState) context)
					.getPositionOfAgent(this.getBody());
			// check that the other agent is in range of this agent
			if (Math.abs(otherposition.getFirst() - selfposition.getFirst()) <= 1
					&& Math.abs(otherposition.getSecond()
							- selfposition.getSecond()) <= 1) {
				System.out.println(Math.abs(otherposition.getFirst() - selfposition.getFirst()) + " " +
					Math.abs(otherposition.getSecond()
							- selfposition.getSecond()));
				return true;
			}
		}
		return false;
	}
}
