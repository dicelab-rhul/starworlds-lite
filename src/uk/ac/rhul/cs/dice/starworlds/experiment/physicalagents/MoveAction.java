package uk.ac.rhul.cs.dice.starworlds.experiment.physicalagents;

import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.PhysicalAction;
import uk.ac.rhul.cs.dice.starworlds.environment.State;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.Physics;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.SensorSubscriber.SensiblePerception;
import uk.ac.rhul.cs.dice.starworlds.perception.AbstractPerception;
import uk.ac.rhul.cs.dice.starworlds.perception.DefaultPerception;
import uk.ac.rhul.cs.dice.starworlds.utils.Pair;

public class MoveAction extends PhysicalAction {

	@SensiblePerception
	public static final Class<?> POSSIBLEPERCEPTION = DefaultPerception.class;

	private Pair<Integer, Integer> moveFrom;
	private Pair<Integer, Integer> moveTo;

	public MoveAction(int i, int j) {
		this.moveTo = new Pair<>(i, j);
	}

	@Override
	public Set<AbstractPerception<?>> getAgentPerceptions(Physics physics,
			State context) {
		return ((PhysicalPhysics) physics).getAgentPerceptions(this,
				(PhysicalState) context);
	}

	@Override
	public Set<AbstractPerception<?>> getOthersPerceptions(Physics physics,
			State context) {
		return ((PhysicalPhysics) physics).getOthersPerceptions(this,
				(PhysicalState) context);
	}

	@Override
	public boolean perform(Physics physics, State context) {
		return ((PhysicalPhysics) physics).perform(this,
				(PhysicalState) context);
	}

	@Override
	public boolean isPossible(Physics physics, State context) {
		if (PhysicalPhysics.class.isAssignableFrom(physics.getClass())
				&& PhysicalState.class.isAssignableFrom(context.getClass())) {
			return ((PhysicalPhysics) physics).isPossible(this,
					(PhysicalState) context);
		}
		return false;
	}

	@Override
	public boolean verify(Physics physics, State context) {
		return ((PhysicalPhysics) physics)
				.verify(this, (PhysicalState) context);
	}

	@Override
	public Object[] getCanSense() {
		return null;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " " + this.moveTo;
	}

	public Pair<Integer, Integer> getMoveFrom() {
		return moveFrom;
	}

	public void setMoveFrom(Pair<Integer, Integer> moveFrom) {
		this.moveFrom = moveFrom;
	}

	public Pair<Integer, Integer> getMoveTo() {
		return moveTo;
	}

	public void setMoveTo(Pair<Integer, Integer> moveTo) {
		this.moveTo = moveTo;
	}

}
