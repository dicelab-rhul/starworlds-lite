package uk.ac.rhul.cs.dice.starworlds.initialisation;

import java.util.HashSet;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.AbstractWorld;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.Universe;

public class WorldDeployer {

	private final static Set<Thread> WORLDS = new HashSet<>();

	public static void deploy(AbstractWorld world) {
		world.accept(new ConnectionInitialisationVisitor());
		world.accept(new InfoPrintVisitor());
		world.accept(new PostInitialisationVisitor());
		Thread t = new Thread((Universe) world.getRoot().getValue());
		WORLDS.add(t);
		t.start();
	}

	public static Set<Thread> getWorlds() {
		return WORLDS;
	}
}
