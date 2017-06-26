package uk.ac.rhul.cs.dice.starworlds.experiment.physicalagents;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SensingAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.ActiveBodyAppearance;
import uk.ac.rhul.cs.dice.starworlds.appearances.Appearance;
import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.PassiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.AbstractAmbient;
import uk.ac.rhul.cs.dice.starworlds.utils.Pair;

public class PhysicalState extends AbstractAmbient {

	private static final String DIMENSIONKEY = "DIMENSION";
	private static final String GRIDKEY = "GRID";
	private static final String LOCALKEY = "LOCAL";
	private static final String LOCALAGENT = "LOCALAGENT";

	private Integer dimension;

	private Map<ActiveBodyAppearance, Pair<Integer, Integer>> grid = new HashMap<>();
	private Map<Pair<Integer, Integer>, ActiveBodyAppearance> inversegrid = new HashMap<>();

	public PhysicalState(Set<AbstractAgent> agents,
			Set<ActiveBody> activeBodies, Set<PassiveBody> passiveBodies) {
		super(agents, activeBodies, passiveBodies);
		super.addEnvironmentVariable(GRIDKEY, this.grid);
		super.addFilter(LOCALKEY, new LocalFilter());
		super.addFilter(LOCALAGENT, new LocalAgentFilter());
	}

	/**
	 * An initialisation method that called by the
	 * {@link PhysicalEnvironment#postInitialisation()} method (and only by that
	 * method) to set up the dimension of this {@link PhysicalState}.
	 * 
	 * @param dimension
	 *            to set
	 * @throws IllegalStateException
	 *             if multiple attempts are made to change the dimension (i.e.
	 *             if this method is called more than once)
	 */
	public void setDimension(int dimension) {
		if (this.dimension == null) {
			super.addEnvironmentVariable(DIMENSIONKEY, dimension);
			this.dimension = dimension;
			this.getAgents().forEach(
					(AbstractAgent a) -> {
						Set<Pair<Integer, Integer>> filled = new HashSet<>();
						Pair<Integer, Integer> position = new Pair<>(
								(int) (Math.random() * this.dimension),
								(int) (Math.random() * this.dimension));
						while (filled.contains(position)) {
							position = new Pair<>(
									(int) (Math.random() * this.dimension),
									(int) (Math.random() * this.dimension));
						}
						grid.put(a.getAppearance(), position);
						inversegrid.put(position, a.getAppearance());
					});
			printGrid();
		} else {
			throw new IllegalStateException("Cannot reset dimension: "
					+ this.getDimension() + " to: " + dimension + " for: "
					+ this.getClass().getSimpleName()
					+ " after it has been set.");
		}
	}

	public void printGrid() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < this.dimension; i++) {
			for (int j = 0; j < this.dimension; j++) {
				builder.append("[ ]");
			}
			builder.append(System.lineSeparator());
		}
		grid.values().forEach(
				(Pair<Integer, Integer> pair) -> {
					int start = ((pair.getSecond() * this.dimension + pair
							.getFirst()) * 3) + pair.getSecond() * 2;
					int end = start + 3;
					builder.replace(start, end, "["
							+ inversegrid.get(pair).getId() + "]");
					System.out.println(pair);
				});
		System.out.println(builder.toString());
	}

	public Map<ActiveBodyAppearance, Pair<Integer, Integer>> getGrid() {
		return grid;
	}

	public void updateGrid(Pair<Integer, Integer> position,
			ActiveBodyAppearance body) {
		inversegrid.remove(grid.get(body));
		grid.put(body, position);
		inversegrid.put(position, body);
	}

	public boolean positionFilled(Pair<Integer, Integer> position) {
		return inversegrid.containsKey(position);
	}

	public int getDimension() {
		return dimension;
	}

	public ActiveBodyAppearance getAgentAt(Pair<Integer, Integer> position) {
		return inversegrid.get(position);
	}

	public Pair<Integer, Integer> getPositionOfAgent(ActiveBody body) {
		return this.grid.get(body.getAppearance());
	}

	public Pair<Integer, Integer> getPositionOfAgent(
			ActiveBodyAppearance appearance) {
		return this.grid.get(appearance);
	}

	public class LocalAgentFilter extends AppearanceFilter {
		@Override
		public Set<Appearance> get(SensingAction action, Object... args) {
			Set<Appearance> result = new HashSet<>();
			Set<?> pairs = (Set<?>) args[0];
			pairs.forEach((Object o) -> {
				result.add((inversegrid.get(o)));
			});
			result.remove(null);
			return result;
		}
	}

	public class LocalFilter implements Filter {

		@Override
		public Set<Pair<Integer, Integer>> get(SensingAction action,
				Object... args) {
			// Map<ActiveBody, Pair<Integer, Integer>>
			Map<?, ?> map = (Map<?, ?>) args[0];
			Pair<?, ?> location = (Pair<?, ?>) map.get(action.getActor());
			Integer x = (Integer) location.getFirst();
			Integer y = (Integer) location.getSecond();
			Set<Pair<Integer, Integer>> result = new HashSet<>();
			for (int i = -1; i < 2; i++) {
				for (int j = -1; j < 2; j++) {
					result.add(new Pair<>((x + i >= 0 && x + i < dimension) ? x
							+ i : x, (y + j >= 0 && y + j < dimension) ? y + j
							: y));
				}
			}
			result.remove(new Pair<>(x, y));
			return result;
		}
	}

}
