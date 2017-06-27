package uk.ac.rhul.cs.dice.starworlds.initialisation;

import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.AbstractConnectedEnvironment;
import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.tree.GraphTreeNode;
import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.visitor.Acceptor;
import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.visitor.Visitor;

public class InfoPrintVisitor implements Visitor<AbstractConnectedEnvironment> {
	@Override
	public void visit(Acceptor<AbstractConnectedEnvironment> acceptor) {
		System.out
				.println(((GraphTreeNode<AbstractConnectedEnvironment>) acceptor)
						.getValue().getConnectedEnvironmentManager());
	}
}
