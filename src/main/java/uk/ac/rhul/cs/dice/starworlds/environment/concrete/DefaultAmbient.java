package uk.ac.rhul.cs.dice.starworlds.environment.concrete;

import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.PassiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractAutonomousAgent;
import uk.ac.rhul.cs.dice.starworlds.entities.avatar.AbstractAvatarAgent;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.AbstractAmbient;

public class DefaultAmbient extends AbstractAmbient {

	public DefaultAmbient(Set<AbstractAutonomousAgent> agents,
			Set<ActiveBody> activeBodies, Set<PassiveBody> passiveBodies,
			Set<AbstractAvatarAgent<?>> avatars) {
		super(agents, activeBodies, passiveBodies, avatars);
	}
}
