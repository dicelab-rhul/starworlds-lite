package uk.ac.rhul.cs.dice.starworlds.entities.avatar;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;

public class DefaultSelfishAvatarMind extends AbstractSelfishAvatarMind<AbstractEnvironmentalAction> {

	@Override
	public void showAvatarView(Collection<Perception<?>> perceptions) {
		System.out.println("AVATAR VIEW: " + perceptions);
	}

}
