package uk.ac.rhul.cs.dice.starworlds.test;

import uk.ac.rhul.cs.dice.starworlds.perception.SerializablePerception;

public class TestSerializable {

	public static void main(String[] args) {
		SerializablePerceptionTestClass<TestPayload> p1 = new SerializablePerceptionTestClass<>(
				new TestPayload());
		SerializablePerception<?> p2 = SerializablePerception
				.convertToSerializable(p1);
		System.out.println(p2);
		System.out.println(SerializablePerception.convertBack(p2));

	}

}
