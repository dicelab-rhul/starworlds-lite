package uk.ac.rhul.cs.dice.starworlds.experiment.communicatingagents;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.Universe;
import uk.ac.rhul.cs.dice.starworlds.parser.Parser;

class ExperimentCommunication {

	private static final String PATH = "init/CommunicationExperiment/";

	private static final String SINGLE = "Single.json";
	private static final String MCHAIN = "MultipleChain.json";
	private static final String MTREE = "MultipleTree.json";
	private static final String REMOTE = "Remote.json";
	private static final String MASTERSLAVE = "MasterSlave.json";

	private static Parser PARSER;

	public static void main(String[] args) throws Exception {
		PARSER = new Parser(PATH + MTREE);
		Collection<Universe> multiverse = PARSER.parse();
		for (Universe u : multiverse) {
			new Thread(u).start();
		}
	}
}
