package uk.ac.rhul.cs.dice.starworlds.initialisation;

import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.AbstractConnectedEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.AbstractWorld;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.WorldNode;
import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.tree.Node;
import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.visitor.Acceptor;
import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.visitor.Visitor;

/**
 * A {@link Visitor} class that {@link Visitor#visit(Acceptor) visits}
 * {@link WorldNode}s and calls the
 * {@link AbstractConnectedEnvironment#initialiseEnvironmentConnections(java.util.Collection, java.util.Collection)}
 * method. See this method for details. This {@link Visitor} is used in the
 * {@link WorldDeployer} to initialise the {@link AbstractWorld World}.
 * 
 * @author Ben Wilkins
 *
 */
public class ConnectionInitialisationVisitor implements
		Visitor<AbstractConnectedEnvironment> {
	@Override
	public void visit(Acceptor<AbstractConnectedEnvironment> acceptor) {
		WorldNode node = (WorldNode) acceptor;
		node.getValue().initialiseEnvironmentConnections(
				Node.getValuesFrom(node.getChildren()),
				Node.getValuesFrom(node.getNeighbours()));
	}
}
