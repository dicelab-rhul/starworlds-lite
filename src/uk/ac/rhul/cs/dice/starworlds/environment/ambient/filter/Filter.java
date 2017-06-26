package uk.ac.rhul.cs.dice.starworlds.environment.ambient.filter;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SensingAction;

public interface Filter {
	public Object get(SensingAction action, Object... args);

	public static void missuse(Filter filter) {
		System.err.println("Error: Miss use of filter: " + filter);
	}
}
