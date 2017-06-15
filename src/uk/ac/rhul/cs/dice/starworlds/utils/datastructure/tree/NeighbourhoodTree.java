package uk.ac.rhul.cs.dice.starworlds.utils.datastructure.tree;

public class NeighbourhoodTree<T> extends Tree<T> {

	public NeighbourhoodTree() {
	}

	public NeighbourhoodTree(NeighbourhoodNode<T> root) {
		super(root);
	}

	@Override
	public NeighbourhoodNode<T> getRoot() {
		return (NeighbourhoodNode<T>) super.getRoot();
	}

	public void setRoot(NeighbourhoodNode<T> root) {
		super.setRoot(root);
	}

	@Override
	public void setRoot(Node<T> root) {
		super.setRoot((NeighbourhoodNode<T>) root);
	}
	
	
	
}
