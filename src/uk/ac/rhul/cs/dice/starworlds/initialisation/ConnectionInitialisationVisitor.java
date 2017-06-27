package uk.ac.rhul.cs.dice.starworlds.initialisation;

import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.AbstractConnectedEnvironment;
import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.tree.GraphTreeNode;
import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.tree.Node;
import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.visitor.Acceptor;
import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.visitor.Visitor;

public class ConnectionInitialisationVisitor implements
		Visitor<AbstractConnectedEnvironment> {
	@Override
	public void visit(Acceptor<AbstractConnectedEnvironment> acceptor) {
		GraphTreeNode<AbstractConnectedEnvironment> node = (GraphTreeNode<AbstractConnectedEnvironment>) acceptor;
		node.getValue().initialiseEnvironmentConnections(
				Node.getValuesFrom(node.getChildren()),
				Node.getValuesFrom(node.getNeighbours()));
	}
}
