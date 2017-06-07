package uk.ac.rhul.cs.dice.starworlds.test;

import uk.ac.rhul.cs.dice.starworlds.perception.AbstractPerception;

public class SerializablePerceptionTestClass<T> extends AbstractPerception<T> {
	public SerializablePerceptionTestClass(T content) {
		super(content);
	}
}
