package uk.ac.rhul.cs.dice.starworlds.experiment.communicatingagents;

import java.io.IOException;

import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.Universe;
import uk.ac.rhul.cs.dice.starworlds.parser.Parser;

class ExperimentCommunication {

	private static final String INITFILESINGLE = "init/CommunicationExperimentSingleInit.json";
	private static final String INITFILEMULTI = "init/CommunicationExperimentInit.json";
	private static Parser PARSER;

	public static void main(String[] args) throws IOException {
		PARSER = new Parser(INITFILEMULTI);
		Universe universe = PARSER.parse();
		universe.simulate();
	}
}
