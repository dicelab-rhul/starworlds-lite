package uk.ac.rhul.cs.dice.starworlds.environment.ambient.filter;

import java.util.HashSet;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.Appearance;

public class SelfFilter extends AppearanceFilter {
	@Override
	public Set<Appearance> get(AbstractEnvironmentalAction action, Object... args) {
		Set<Appearance> self = new HashSet<>();
		self.add(action.getActor());
		return self;
	}
}
