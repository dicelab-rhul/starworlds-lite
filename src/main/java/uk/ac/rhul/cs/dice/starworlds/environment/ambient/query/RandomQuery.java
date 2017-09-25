package uk.ac.rhul.cs.dice.starworlds.environment.ambient.query;

import java.util.Collection;
import java.util.Optional;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;

public class RandomQuery implements Query {

	@Override
	public Object get(AbstractEnvironmentalAction action, Object... args) {
		// we dont need the action for this
		if (Collection.class.isAssignableFrom(args[0].getClass())) {
			Collection<?> col = (Collection<?>) args[0];
			Optional<?> o = col.stream()
					.skip((long) (Math.random() * col.size())).findAny();
			if (o != null) {
				if (o.isPresent()) {
					return o.get();
				} else {
					return null;
				}
			}
		}
		Query.missuse(this);
		return null;
	}
}