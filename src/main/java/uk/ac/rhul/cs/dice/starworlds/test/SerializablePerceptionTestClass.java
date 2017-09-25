package uk.ac.rhul.cs.dice.starworlds.test;

import uk.ac.rhul.cs.dice.starworlds.perception.AbstractPerception;

public class SerializablePerceptionTestClass<T> extends AbstractPerception<T> {
    private static final long serialVersionUID = -645533770395312975L;

    public SerializablePerceptionTestClass(T content) {
	super(content);
    }
}