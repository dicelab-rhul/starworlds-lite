package uk.ac.rhul.cs.dice.starworlds.experiment.communicatingagents;

import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.Universe;
import uk.ac.rhul.cs.dice.starworlds.parser.Parser;

class ExperimentCommunication {

	private static final String INITFILESINGLE = "init/CommunicationExperimentSingleInit.json";
	private static final String INITFILEMULTI = "init/CommunicationExperimentInit.json";
	private static final String INITFILEREMOTE = "init/CommunicationExperimentInitRemote.json";
	private static Parser PARSER;

	public static void main(String[] args) throws Exception {
		PARSER = new Parser(INITFILEMULTI);
		Universe universe = PARSER.parse();
		universe.simulate();
	}
}
