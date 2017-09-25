package uk.ac.rhul.cs.dice.starworlds.environment.ambient.query;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.Appearance;
import uk.ac.rhul.cs.dice.starworlds.appearances.PhysicalBodyAppearance;
import uk.ac.rhul.cs.dice.starworlds.entities.PhysicalBody;

public class AppearanceQuery implements Query {
	@Override
	public Set<Appearance> get(AbstractEnvironmentalAction action,
			Object... args) {
		Set<Appearance> appearances = new HashSet<>();
		if (Collection.class.isAssignableFrom(args[0].getClass())) {
			Collection<?> col = (Collection<?>) args[0];
			PhysicalBodyAppearance actor = action.getActor();
			col.forEach((Object o) -> {
				PhysicalBody body = (PhysicalBody) o;
				if (!body.getAppearance().equals(actor)) {
					appearances.add(body.getAppearance());
				}
			});
			return appearances;
		} else if (PhysicalBody.class.isAssignableFrom(args[0].getClass())) {
			appearances.add(((PhysicalBody) args[0]).getAppearance());
			return appearances;
		}
		Query.missuse(this);
		return null;
	}
}