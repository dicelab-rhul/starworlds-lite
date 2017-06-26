package uk.ac.rhul.cs.dice.starworlds.experiment.physicalagents;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.Universe;
import uk.ac.rhul.cs.dice.starworlds.parser.Parser;

public class PhysicalExperiment {

	private static String file = "init/PhysicalExperiment/Physical.json";

	public static void main(String[] args) throws Exception {
		Parser parser = new Parser(file);
		Collection<Universe> multiverse = parser.parse();
		multiverse.forEach((u) -> u.simulate());
	}
}
