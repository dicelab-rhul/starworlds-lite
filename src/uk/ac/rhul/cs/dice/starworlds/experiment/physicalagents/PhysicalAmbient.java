package uk.ac.rhul.cs.dice.starworlds.experiment.physicalagents;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.ActiveBodyAppearance;
import uk.ac.rhul.cs.dice.starworlds.appearances.Appearance;
import uk.ac.rhul.cs.dice.starworlds.appearances.PhysicalBodyAppearance;
import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.PassiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.PhysicalBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractAutonomousAgent;
import uk.ac.rhul.cs.dice.starworlds.entities.avatar.AbstractAvatarAgent;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.AbstractAmbient;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.query.AppearanceQuery;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.query.Query;
import uk.ac.rhul.cs.dice.starworlds.utils.Pair;

public class PhysicalAmbient extends AbstractAmbient {

	private static final String DIMENSIONKEY = "DIMENSION";
	private static final String GRIDKEY = "GRID";
	private static final String LOCALKEY = "LOCAL";
	private static final String LOCALAGENT = "LOCALAGENT";

	private Integer dimension;

	private Map<PhysicalBodyAppearance, Pair<Integer, Integer>> grid = new HashMap<>();
	private Map<Pair<Integer, Integer>, PhysicalBodyAppearance> inversegrid = new HashMap<>();

	public PhysicalAmbient(Set<AbstractAutonomousAgent> agents,
			Set<ActiveBody> activeBodies, Set<PassiveBody> passiveBodies,
			Set<AbstractAvatarAgent<?>> avatars) {
		super(agents, activeBodies, passiveBodies, avatars);
		super.addEnvironmentVariable(GRIDKEY, this.grid);
		super.addFilter(LOCALKEY, new LocalFilter());
		super.addFilter(LOCALAGENT, new LocalAgentFilter());
	}

	/**
	 * An initialisation method that called by the
	 * {@link PhysicalUniverse#postInitialisation()} method (and only by that
	 * method) to set up the dimension of this {@link PhysicalAmbient}.
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
			fillGrid(avatars.values());
			fillGrid(agents.values());
			fillGrid(activeBodies.values());
			fillGrid(passiveBodies.values());
			printGrid();
		} else {
			throw new IllegalStateException("Cannot reset dimension: "
					+ this.getDimension() + " to: " + dimension + " for: "
					+ this.getClass().getSimpleName()
					+ " after it has been set.");
		}
	}

	private void fillGrid(Collection<? extends PhysicalBody> bodies) {
		bodies.forEach((PhysicalBody a) -> {
			Set<Pair<Integer, Integer>> filled = new HashSet<>();
			Pair<Integer, Integer> position = new Pair<>(
					(int) (Math.random() * this.dimension), (int) (Math
							.random() * this.dimension));
			while (filled.contains(position)) {
				position = new Pair<>((int) (Math.random() * this.dimension),
						(int) (Math.random() * this.dimension));
			}
			grid.put(a.getAppearance(), position);
			inversegrid.put(position, a.getAppearance());
		});
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
							+ inversegrid.get(pair).getId().charAt(0) + "]");
					System.out.println(pair);
				});
		System.out.println(builder.toString());
	}

	public Map<PhysicalBodyAppearance, Pair<Integer, Integer>> getGrid() {
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

	public PhysicalBodyAppearance getAgentAt(Pair<Integer, Integer> position) {
		return inversegrid.get(position);
	}

	public Pair<Integer, Integer> getPositionOfAgent(ActiveBody body) {
		return this.grid.get(body.getAppearance());
	}

	public Pair<Integer, Integer> getPositionOfAgent(
			ActiveBodyAppearance appearance) {
		return this.grid.get(appearance);
	}

	public class LocalAgentFilter extends AppearanceQuery {
		@Override
		public Set<Appearance> get(AbstractEnvironmentalAction action,
				Object... args) {
			Set<Appearance> result = new HashSet<>();
			Set<?> pairs = (Set<?>) args[0];
			pairs.forEach((Object o) -> {
				result.add((inversegrid.get(o)));
			});
			result.remove(null);
			return result;
		}
	}

	public class LocalFilter implements Query {

		@Override
		public Set<Pair<Integer, Integer>> get(
				AbstractEnvironmentalAction action, Object... args) {
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
