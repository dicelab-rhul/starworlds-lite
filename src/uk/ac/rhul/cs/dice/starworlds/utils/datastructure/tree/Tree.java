package uk.ac.rhul.cs.dice.starworlds.utils.datastructure.tree;

import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.visitor.Acceptor;
import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.visitor.Visitor;

/**
 * A simple tree data structure.
 * 
 * @author Ben
 *
 * @param <T>
 */
public class Tree<T> implements Acceptor<T> {

	private Node<T> root;

	public Tree() {
	}

	public Tree(Node<T> root) {
		this.setRoot(root);
	}

	public Node<T> getRoot() {
		return root;
	}

	public void setRoot(Node<T> root) {
		this.root = root;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder(this.getClass()
				.getSimpleName() + System.lineSeparator());
		recurseToString(root, "-", builder);
		return builder.toString();
	}

	private void recurseToString(Node<T> node, String indent,
			StringBuilder builder) {
		String newIndent = "  " + indent;
		builder.append(newIndent + node + System.lineSeparator());
		if (node.hasChildren()) {
			node.getChildren().forEach(
					(Node<T> n) -> recurseToString(n, newIndent, builder));
		}
	}

	@Override
	public void accept(Visitor<T> visitor) {
		root.accept(visitor);
	}
}
