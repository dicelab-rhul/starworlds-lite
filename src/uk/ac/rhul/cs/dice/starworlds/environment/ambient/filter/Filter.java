package uk.ac.rhul.cs.dice.starworlds.environment.ambient.filter;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;

public interface Filter {
	public Object get(AbstractEnvironmentalAction action, Object... args);

	public static void missuse(Filter filter) {
		System.err.println("Error: Miss use of filter: " + filter);
	}
}
