package uk.ac.rhul.cs.dice.starworlds.experiment.physicalagents;

import java.util.Arrays;

import uk.ac.rhul.cs.dice.starworlds.appearances.ActiveBodyAppearance;
import uk.ac.rhul.cs.dice.starworlds.perception.DefaultPerception;
import uk.ac.rhul.cs.dice.starworlds.utils.Pair;

public class MovePerception extends DefaultPerception<Object[]> {

	public MovePerception(ActiveBodyAppearance activebody,
			Pair<Integer, Integer> movedFrom, Pair<Integer, Integer> movedTo) {
		super(new Object[] { activebody, movedFrom, movedTo });
	}

	public ActiveBodyAppearance getActivebody() {
		return (ActiveBodyAppearance) this.getPerception()[0];
	}

	@SuppressWarnings("unchecked")
	public Pair<Integer, Integer> getMovedFrom() {
		return (Pair<Integer, Integer>) this.getPerception()[1];
	}

	@SuppressWarnings("unchecked")
	public Pair<Integer, Integer> getMovedTo() {
		return (Pair<Integer, Integer>) this.getPerception()[2];
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getClass().getSimpleName() + ": "
				+ Arrays.toString(this.getPerception());
	}
}
