package uk.ac.rhul.cs.dice.starworlds.environment.ambient.query;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;

public interface Query {
	public Object get(AbstractEnvironmentalAction action, Object... args);

	public static void missuse(Query query) {
		System.err.println("Error: Miss use of query: " + query);
	}
}
