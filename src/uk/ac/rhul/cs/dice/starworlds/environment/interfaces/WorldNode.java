package uk.ac.rhul.cs.dice.starworlds.environment.interfaces;

import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.tree.GraphTreeNode;

public class WorldNode extends GraphTreeNode<AbstractConnectedEnvironment> {

	public WorldNode(AbstractConnectedEnvironment value) {
		super(value);
	}

}
