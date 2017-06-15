package uk.ac.rhul.cs.dice.starworlds.utils.datastructure.tree;

import java.util.Collection;

public class NeighbourhoodNode<T> extends Node<T> {

	private Collection<Node<T>> neighbours;

	public NeighbourhoodNode() {
	}

	public NeighbourhoodNode(T value) {
		super(value);
	}

	public NeighbourhoodNode(T value, Collection<Node<T>> children) {
		super(value, children);
	}

	public NeighbourhoodNode(T value, Collection<Node<T>> children,
			Collection<Node<T>> neighbours) {
		super(value, children);
		this.neighbours = neighbours;
	}

	public Collection<Node<T>> getNeighbours() {
		return neighbours;
	}

	public void setNeighbours(Collection<Node<T>> neighbours) {
		this.neighbours = neighbours;
	}

}
