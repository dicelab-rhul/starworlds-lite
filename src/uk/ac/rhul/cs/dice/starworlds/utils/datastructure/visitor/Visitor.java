package uk.ac.rhul.cs.dice.starworlds.utils.datastructure.visitor;

public interface Visitor<T> {

	public void visit(Acceptor<T> acceptor);

}
