package uk.ac.rhul.cs.dice.starworlds.utils;

public class AbstractSingleTypedPair<T> extends AbstractPair<T, T> {

	private static final long serialVersionUID = -8844016509514944835L;

	public AbstractSingleTypedPair(T first, T second) {
		super(first, second);
	}
}