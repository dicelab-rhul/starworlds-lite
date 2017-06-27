package uk.ac.rhul.cs.dice.starworlds.utils.datastructure.tree;

import java.util.ArrayList;
import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.visitor.Acceptor;
import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.visitor.Visitor;

public class Node<T> implements Acceptor<T> {

	private T value;
	private Collection<Node<T>> children;

	public Node() {
	}

	public Node(T value) {
		this.children = new ArrayList<>();
		this.value = value;
	}

	public Node(T value, Collection<Node<T>> children) {
		this.value = value;
		this.children = (children != null) ? children : new ArrayList<>();
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

	public Collection<T> getChildrenValues() {
		return getValuesFrom(this.children);
	}

	public void setChildren(Collection<Node<T>> children) {
		this.children = children;
	}

	public boolean addChild(Node<T> e) {
		return children.add(e);
	}

	public boolean addChildren(Collection<? extends Node<T>> c) {
		return children.addAll(c);
	}

	public void clearChildren() {
		children.clear();
	}

	public boolean removeChild(Object o) {
		return children.remove(o);
	}

	public boolean removeChildren(Collection<?> c) {
		return children.removeAll(c);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "<" + this.value + ">";
	}

	public static <T> Collection<T> getValuesFrom(Collection<Node<T>> nodes) {
		Collection<T> result = new ArrayList<>();
		if (nodes != null) {
			nodes.forEach((node) -> result.add(node.getValue()));
			return result;
		}
		return null;
	}
}
