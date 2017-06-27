package uk.ac.rhul.cs.dice.starworlds.initialisation;

import java.util.HashSet;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.AbstractWorld;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.Universe;
import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.visitor.Visitor;

public class WorldDeployer {

	private final static Set<Thread> WORLDS = new HashSet<>();

	/**
	 * Deploys the given {@link AbstractWorld} and starts the simulation via
	 * {@link Universe#simulate()}. The world is run in its own thread.
	 * 
	 * @param world
	 *            to deploy and run
	 */
	public static void deployAndRun(AbstractWorld world) {
		Thread t = new Thread(new WorldDeployer().initialiseWorld(world));
		WORLDS.add(t);
		t.start();
	}

	/**
	 * Deploys the given {@link AbstractWorld}. This method will not start the
	 * simulation and returns the {@link Universe}.
	 * 
	 * @param world
	 *            to deploy
	 * @return the universe
	 */
	public static Universe deploy(AbstractWorld world) {
		return new WorldDeployer().initialiseWorld(world);
	}

	/**
	 * Initialise the given {@link AbstractWorld World}. The
	 * {@link AbstractWorld World} is traversed as a depth first search using
	 * {@link Visitor}s, initialisation has 2 phases: The first {@link Visitor}
	 * is {@link ConnectionInitialisationVisitor}, the second is
	 * {@link PostInitialisationVisitor}. See classes for details. This method
	 * may be overridden if additional {@link AbstractWorld World}
	 * initialisation is required. However it is recommended that this method is
	 * always called first.
	 *
	 * TODO traverse neighbours!
	 * 
	 * @param world
	 *            to initialise
	 * @return the {@link Universe}
	 */
	protected Universe initialiseWorld(AbstractWorld world) {
		world.accept(new ConnectionInitialisationVisitor());
		// world.accept(new InfoPrintVisitor());
		world.accept(new PostInitialisationVisitor());
		return (Universe) world.getRoot().getValue();
	}

	public static Set<Thread> getWorlds() {
		return WORLDS;
	}
}
