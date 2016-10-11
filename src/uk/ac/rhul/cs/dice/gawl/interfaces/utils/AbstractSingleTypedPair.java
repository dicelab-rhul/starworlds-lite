package uk.ac.rhul.cs.dice.gawl.interfaces.utils;

public class AbstractSingleTypedPair<T> extends AbstractPair<T, T> {

	public AbstractSingleTypedPair(T first, T second) {
		super(first, second);
	}
}