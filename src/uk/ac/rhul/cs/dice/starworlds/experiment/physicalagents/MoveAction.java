package uk.ac.rhul.cs.dice.starworlds.experiment.physicalagents;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.PhysicalAction;
import uk.ac.rhul.cs.dice.starworlds.environment.subscriber.AbstractSubscriber.SensiblePerception;
import uk.ac.rhul.cs.dice.starworlds.utils.Pair;

public class MoveAction extends PhysicalAction {

	private static final long serialVersionUID = 4286281855759144411L;

	@SensiblePerception
	public static final Class<?> POSSIBLEPERCEPTION = MovePerception.class;

	private Pair<Integer, Integer> moveFrom;
	private Pair<Integer, Integer> moveTo;

	public MoveAction(int i, int j) {
		this.moveTo = new Pair<>(i, j);
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
