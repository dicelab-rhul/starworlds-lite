package uk.ac.rhul.cs.dice.starworlds.experiment.vacuumworld;

import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.Universe;
import uk.ac.rhul.cs.dice.starworlds.parser.Parser;

public class VacuumWorld {

	private static String initfile = "VacuumWorld/Default.json";

	public static void main(String[] args) throws Exception {
		Parser p = new Parser(initfile);
		Universe u = p.parse().toArray(new Universe[1])[0];
		u.simulate();
	}
}
