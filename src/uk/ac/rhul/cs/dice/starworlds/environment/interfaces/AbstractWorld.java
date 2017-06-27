package uk.ac.rhul.cs.dice.starworlds.environment.interfaces;

import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.tree.GraphTree;
import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.tree.GraphTreeNode;
import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.tree.Node;

public abstract class AbstractWorld extends
		GraphTree<AbstractConnectedEnvironment> {

	public AbstractWorld(Universe universe) {
		super(new WorldNode((AbstractConnectedEnvironment) universe));
	}

	@Override
	public WorldNode getRoot() {
		return (WorldNode) super.getRoot();
	}

	public void setRoot(WorldNode root) {
		super.setRoot(root);
	}

	@Override
	public void setRoot(Node<AbstractConnectedEnvironment> root) {
		super.setRoot((WorldNode) root);
	}

	@Override
	public void setRoot(GraphTreeNode<AbstractConnectedEnvironment> root) {
		super.setRoot((WorldNode) root);
	}
}