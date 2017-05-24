package uk.ac.rhul.cs.dice.gawl.interfaces.environment.concrete;

import java.util.Set;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.environmental.EnvironmentalAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.environmental.PhysicalAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.ActiveBody;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.PassiveBody;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.State;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.AbstractPhysics;

public class DefaultPhysics extends AbstractPhysics {

	public DefaultPhysics(Set<AbstractAgent> agents,
			Set<ActiveBody> activeBodies, Set<PassiveBody> passiveBodies) {
		super(agents, activeBodies, passiveBodies);
	}

	@Override
	public void agentsPerceive() {
		System.out.println("PERCEIVE");
		super.agentsPerceive();
	}

	@Override
	public void agentsDecide() {
		System.out.println("DECIDE");
		super.agentsDecide();
	}

	@Override
	public void agentsExecute() {
		System.out.println("EXECUTE");
		super.agentsExecute();
	}

	@Override
	public boolean perform(PhysicalAction action, State context) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPossible(PhysicalAction action, State context) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean verify(PhysicalAction action, State context) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void perform(EnvironmentalAction action, State context) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean verify(EnvironmentalAction action, State context) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPossible(EnvironmentalAction action, State context) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void execute(PhysicalAction action, State context) {
		// TODO Auto-generated method stub

	}

	@Override
	public void execute(EnvironmentalAction action, State context) {
		// TODO Auto-generated method stub

	}

}
