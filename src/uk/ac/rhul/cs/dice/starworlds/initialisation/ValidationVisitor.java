package uk.ac.rhul.cs.dice.starworlds.initialisation;

import java.util.Collection;
import java.util.HashSet;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.CommunicationAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SensingAction;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.AbstractConnectedEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.AbstractWorld;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.Environment;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.WorldNode;
import uk.ac.rhul.cs.dice.starworlds.parser.ReflectiveMethodStore;
import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.visitor.Acceptor;
import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.visitor.Visitor;

/**
 * A {@link Visitor} class that {@link Visitor#visit(Acceptor) visits}
 * {@link WorldNode}s and attempts to validate the {@link Environment}. That is,
 * to ensure that all methods called via reflection have been defined by a
 * developer. See {@link ReflectiveMethodStore} for details. This
 * {@link Visitor} is used in the {@link WorldDeployer} to initialise the
 * {@link AbstractWorld World}.
 * 
 * @author Ben Wilkins
 *
 */
public class ValidationVisitor implements Visitor<AbstractConnectedEnvironment> {

	public static final Collection<Class<? extends AbstractEnvironmentalAction>> IGNORE = new HashSet<>();
	static {
		IGNORE.add(SensingAction.class);
		IGNORE.add(CommunicationAction.class);
	}

	@Override
	public void visit(Acceptor<AbstractConnectedEnvironment> acceptor) {
		WorldNode node = (WorldNode) acceptor;
		AbstractConnectedEnvironment env = node.getValue();
		Collection<Class<? extends AbstractEnvironmentalAction>> actions = new HashSet<>(
				env.getPossibleActions());
		actions.removeAll(IGNORE);
		ReflectiveMethodStore.validateReflectiveActions(env.getPhysics()
				.getClass(), actions);
		// TODO validate sensors?
	}
}
