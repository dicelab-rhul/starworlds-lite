package uk.ac.rhul.cs.dice.starworlds.experiment.physicalagents;

import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.starworlds.environment.AbstractState;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractPhysics;

public class PhysicalState extends AbstractState {

	private static final String DIMENSIONKEY = "DIMENSION";
	private static final String GRIDKEY = "GRID";

	
	private int dimension;
	private ActiveBody[][] grid;
	rivate 
	
	public PhysicalState(AbstractPhysics physics, int dimension) {
		super(physics);
		this.dimension = dimension;
		super.addEnvironmentVariable(DIMENSIONKEY, dimension);
		this.grid = new ActiveBody[this.dimension][ths,dimension];
		super.addEnvironmentVariable(GRIDKEY, this.grid);
		physics.getAgents().forEach((AbstractAgent a) -> {
			
		});

	}
}
