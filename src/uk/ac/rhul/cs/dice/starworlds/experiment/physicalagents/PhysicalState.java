package uk.ac.rhul.cs.dice.starworlds.experiment.physicalagents;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SensingAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.ActiveBodyAppearance;
import uk.ac.rhul.cs.dice.starworlds.appearances.Appearance;
import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.starworlds.environment.AbstractState;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractPhysics;
import uk.ac.rhul.cs.dice.starworlds.utils.Pair;

public class PhysicalState extends AbstractState {

	private static final String DIMENSIONKEY = "DIMENSION";
	private static final String GRIDKEY = "GRID";
	private static final String LOCALKEY = "LOCAL";
	private static final String LOCALAGENT = "LOCALAGENT";

	private int dimension;
	private Map<ActiveBody, Pair<Integer, Integer>> grid = new HashMap<>();
	private Map<Pair<Integer, Integer>, ActiveBody> inversegrid = new HashMap<>();

	public PhysicalState(AbstractPhysics physics, int dimension) {
		super(physics);
		this.dimension = dimension;
		// add the environment variables
		super.addEnvironmentVariable(GRIDKEY, this.grid);
		super.addEnvironmentVariable(DIMENSIONKEY, dimension);
		super.addFilter(LOCALKEY, new LocalFilter());
		super.addFilter(LOCALAGENT, new LocalAgentFilter());

		physics.getAgents().forEach(
				(AbstractAgent a) -> {
					Set<Pair<Integer, Integer>> filled = new HashSet<>();
					Pair<Integer, Integer> position = new Pair<>((int) (Math
							.random() * this.dimension),
							(int) (Math.random() * this.dimension));
					while (filled.contains(position)) {
						position = new Pair<>(
								(int) (Math.random() * this.dimension),
								(int) (Math.random() * this.dimension));
					}
					grid.put(a, position);
					inversegrid.put(position, a);
				});
		printGrid();
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
					builder.replace(start, end, "[o]");
					System.out.println(pair);
				});
		System.out.println(builder.toString());
	}

	public Map<ActiveBody, Pair<Integer, Integer>> getGrid() {
		return grid;
	}

	public void updateGrid(Pair<Integer, Integer> position, ActiveBody body) {
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

	public ActiveBody getAgentAt(Pair<Integer, Integer> position) {
		return inversegrid.get(position);
	}

	public Pair<Integer, Integer> getPositionOfAgent(ActiveBody body) {
		return this.grid.get(body);
	}

	public Pair<Integer, Integer> getPositionOfAgent(
			ActiveBodyAppearance appearance) {
		return this.grid.get(this.physics.getAgent(appearance));
	}

	public class LocalAgentFilter extends AppearanceFilter {
		@Override
		public Set<Appearance> get(SensingAction action, Object... args) {
			Set<Appearance> result = new HashSet<>();
			Set<?> pairs = (Set<?>) args[0];
			pairs.forEach((Object o) -> {
				result.add((inversegrid.get((Pair<?, ?>) o))
						.getExternalAppearance());
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
