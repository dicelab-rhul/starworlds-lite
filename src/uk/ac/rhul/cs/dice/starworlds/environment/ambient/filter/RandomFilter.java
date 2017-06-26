package uk.ac.rhul.cs.dice.starworlds.environment.ambient.filter;

import java.util.Collection;
import java.util.Optional;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SensingAction;

public class RandomFilter implements Filter {

	@Override
	public Object get(SensingAction action, Object... args) {
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
		Filter.missuse(this);
		return null;
	}
}