package uk.ac.rhul.cs.dice.starworlds.utils.datastructure.visitor;

public interface Acceptor<T> {

	public void accept(Visitor<T> visitor);

}
