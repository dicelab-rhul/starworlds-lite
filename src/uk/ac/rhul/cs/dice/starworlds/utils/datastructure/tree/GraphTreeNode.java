package uk.ac.rhul.cs.dice.starworlds.utils.datastructure.tree;

import java.util.Collection;

public class GraphTreeNode<T> extends Node<T> {

	private Collection<Node<T>> neighbours;

	public GraphTreeNode() {
	}

	public GraphTreeNode(T value) {
		super(value);
	}

	public GraphTreeNode(T value, Collection<Node<T>> children) {
		super(value, children);
	}

	public GraphTreeNode(T value, Collection<Node<T>> children,
			Collection<Node<T>> neighbours) {
		super(value, children);
		this.neighbours = neighbours;
	}

	public Collection<T> getNeighbourValues() {
		return getValuesFrom(this.neighbours);
	}

	public Collection<Node<T>> getNeighbours() {
		return neighbours;
	}

	public void setNeighbours(Collection<Node<T>> neighbours) {
		this.neighbours = neighbours;
	}

}
