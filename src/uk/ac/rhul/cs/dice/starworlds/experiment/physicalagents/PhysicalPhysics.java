package uk.ac.rhul.cs.dice.starworlds.experiment.physicalagents;

import java.util.HashSet;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.appearances.ActiveBodyAppearance;
import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.PassiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.State;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractConnectedPhysics;
import uk.ac.rhul.cs.dice.starworlds.perception.AbstractPerception;
import uk.ac.rhul.cs.dice.starworlds.utils.Pair;

public class PhysicalPhysics extends AbstractConnectedPhysics {

	public PhysicalPhysics(Set<AbstractAgent> agents,
			Set<ActiveBody> activeBodies, Set<PassiveBody> passiveBodies) {
		super(agents, activeBodies, passiveBodies);
	}

	public Set<AbstractPerception<?>> getAgentPerceptions(MoveAction action,
			State context) {
		// perceptions for the agent performing the action
		Set<AbstractPerception<?>> perception = new HashSet<>();
		perception.add(new MovePerception((ActiveBodyAppearance) action
				.getActor(), action.getMoveFrom(), action.getMoveTo()));
		return perception;
	}

	public Set<AbstractPerception<?>> getOtherPerceptions(MoveAction action,
			State context) {
		// perceptions for any other agents in range
		Set<AbstractPerception<?>> perception = new HashSet<>();
		perception.add(new MovePerception((ActiveBodyAppearance) action
				.getActor(), action.getMoveFrom(), action.getMoveTo()));
		return perception;
	}

	public boolean perform(MoveAction action, State context) {
		PhysicalState pcontext = (PhysicalState) context;
		Pair<Integer, Integer> oldposition = pcontext.getGrid().get(
				action.getActor());
		Pair<Integer, Integer> newposition = this.addPosition(oldposition,
				action.getMoveTo());
		pcontext.updateGrid(newposition,
				(ActiveBodyAppearance) action.getActor());
		action.setMoveFrom(oldposition);
		action.setMoveTo(newposition);
		return true;
	}

	public boolean isPossible(MoveAction action, State context) {
		PhysicalState pcontext = (PhysicalState) context;
		Pair<Integer, Integer> result = this.addPosition(pcontext.getGrid()
				.get(action.getActor()), action.getMoveTo());
		return !pcontext.positionFilled(result)
				&& checkBounds(result, pcontext.getDimension());
	}

	public boolean verify(MoveAction action, State context) {
		return true; // TODO
	}

	private Pair<Integer, Integer> addPosition(Pair<Integer, Integer> p1,
			Pair<Integer, Integer> p2) {
		return new Pair<Integer, Integer>(p1.getFirst() + p2.getFirst(),
				p1.getSecond() + p2.getSecond());
	}

	private boolean checkBounds(Pair<Integer, Integer> p, int dimension) {
		return p.getFirst() >= 0 && p.getFirst() < dimension
				&& p.getSecond() > 0 && p.getSecond() < dimension;
	}

	@Override
	public void executeActions() {
		((PhysicalState) environment.getState()).printGrid();
		super.executeActions();
	}

	public boolean perceivable(BadSeeingSensor sensor,
			AbstractPerception<?> perception, State context) {
		if (super.perceivable(sensor, perception, context)) {
			if (MovePerception.class.isAssignableFrom(perception.getClass())) {
				MovePerception mp = (MovePerception) perception;
				Pair<Integer, Integer> otherposition = ((PhysicalState) context)
						.getPositionOfAgent(mp.getActivebody());
				Pair<Integer, Integer> selfposition = ((PhysicalState) context)
						.getPositionOfAgent(sensor.getBody());
				// check that the other agent is in range of this agent
				if (Math.abs(otherposition.getFirst() - selfposition.getFirst()) <= 1
						&& Math.abs(otherposition.getSecond()
								- selfposition.getSecond()) <= 1) {
					return true;
				}
			} else {
				return true;
			}

		}
		return false;
	}
}
