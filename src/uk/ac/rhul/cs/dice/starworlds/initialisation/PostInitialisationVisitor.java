package uk.ac.rhul.cs.dice.starworlds.initialisation;

import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.AbstractConnectedEnvironment;
import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.tree.Node;
import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.visitor.Acceptor;
import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.visitor.Visitor;

public class PostInitialisationVisitor implements
		Visitor<AbstractConnectedEnvironment> {
	@Override
	public void visit(Acceptor<AbstractConnectedEnvironment> acceptor) {
		((Node<AbstractConnectedEnvironment>) acceptor).getValue()
				.postInitialisation();
	}
}
