package uk.ac.rhul.cs.dice.starworlds.experiment.physicalagents;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.PassiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.starworlds.experiment.ExperimentPhysics;
import uk.ac.rhul.cs.dice.starworlds.perception.AbstractPerception;
import uk.ac.rhul.cs.dice.starworlds.perception.DefaultPerception;
import uk.ac.rhul.cs.dice.starworlds.utils.Pair;

public class PhysicalPhysics extends ExperimentPhysics {

	public PhysicalPhysics(
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions,
			Set<AbstractAgent> agents, Set<ActiveBody> activeBodies,
			Set<PassiveBody> passiveBodies) {
		super(possibleActions, agents, activeBodies, passiveBodies);
	}

	public Pair<Set<AbstractPerception<?>>, Set<AbstractPerception<?>>> perform(
			MoveAction action, PhysicalState context) {
		Pair<Set<AbstractPerception<?>>, Set<AbstractPerception<?>>> result;
		// perceptions for the agent performing the action
		Set<AbstractPerception<?>> agent = new HashSet<>();
		// perceptions for any other agents in range
		Set<AbstractPerception<?>> others = new HashSet<>();
		Pair<String, Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> otherperception;
		Pair<Integer, Integer> oldposition = context.getGrid().get(
				action.getActor());
		Pair<Integer, Integer> newposition = this.addPosition(oldposition,
				action.getMoveTo());
		otherperception = new Pair<>("AGENT: "
				+ ((ActiveBody) action.getActor()).getId(), new Pair<>(
				oldposition, newposition));
		others.add(new DefaultPerception<>(otherperception));
		agent.add(new DefaultPerception<>("I HAVE MOVED"));
		action.setMoveFrom(oldposition);
		context.updateGrid(newposition, (ActiveBody) action.getActor());
		result = new Pair<>(agent, others);
		return result;
	}

	public boolean isPossible(MoveAction action, PhysicalState context) {
		Pair<Integer, Integer> result = this.addPosition(
				context.getGrid().get(action.getActor()), action.getMoveTo());
		return !context.positionFilled(result)
				&& checkBounds(result, context.getDimension());
	}

	public boolean verify(MoveAction action, PhysicalState context) {
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
	protected void optional() {
		super.optional();
		((PhysicalState) environment.getState()).printGrid();
	}

}
