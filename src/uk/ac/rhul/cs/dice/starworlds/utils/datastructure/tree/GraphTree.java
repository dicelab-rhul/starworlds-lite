package uk.ac.rhul.cs.dice.starworlds.utils.datastructure.tree;

public class GraphTree<T> extends Tree<T> {

	public GraphTree() {
	}

	public GraphTree(GraphTreeNode<T> root) {
		super(root);
	}

	public GraphTree(T root) {
		super(new GraphTreeNode<T>(root));
	}

	@Override
	public GraphTreeNode<T> getRoot() {
		return (GraphTreeNode<T>) super.getRoot();
	}

	public void setRoot(GraphTreeNode<T> root) {
		super.setRoot(root);
	}

	@Override
	public void setRoot(Node<T> root) {
		super.setRoot((GraphTreeNode<T>) root);
	}

}
