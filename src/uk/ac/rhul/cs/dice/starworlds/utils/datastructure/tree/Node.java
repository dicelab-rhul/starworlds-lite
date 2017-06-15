package uk.ac.rhul.cs.dice.starworlds.utils.datastructure.tree;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.visitor.Acceptor;
import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.visitor.Visitor;

public class Node<T> implements Acceptor<T> {

	private T value;
	private Collection<Node<T>> children;

	public Node() {
	}

	public Node(T value) {
		this.value = value;
	}

	public Node(T value, Collection<Node<T>> children) {
		this.value = value;
		this.children = children;
	}

	@Override
	public void accept(Visitor<T> visitor) {
		visitor.visit(this);
		if (hasChildren()) {
			children.forEach((Node<T> n) -> n.accept(visitor));
		}
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public boolean hasChildren() {
		return !(children == null || children.isEmpty());
	}

	public Collection<Node<T>> getChildren() {
		return children;
	}

	public void setChildren(Collection<Node<T>> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "<" + this.value + ">";
	}

}
