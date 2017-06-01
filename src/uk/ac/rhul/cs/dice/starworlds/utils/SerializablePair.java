package uk.ac.rhul.cs.dice.starworlds.utils;

import java.io.Serializable;

public class SerializablePair<T1 extends Serializable, T2 extends Serializable>
		extends Pair<T1, T2> implements Serializable {

	private static final long serialVersionUID = 5766509615291114245L;

	public SerializablePair(T1 first, T2 second) {
		super(first, second);
	}

	public static <T1 extends Serializable, T2 extends Serializable> SerializablePair<T1, T2> toSerializable(
			Pair<T1, T2> pair) {
		return (SerializablePair<T1, T2>) pair;
	}
}
