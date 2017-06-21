package uk.ac.rhul.cs.dice.starworlds.actions.environmental;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.State;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.Physics;
import uk.ac.rhul.cs.dice.starworlds.parser.ReflectiveMethodStore;
import uk.ac.rhul.cs.dice.starworlds.perception.AbstractPerception;

/**
 * TODO
 * 
 * Known direct subclasses: none.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class PhysicalAction extends AbstractEnvironmentalAction {

	private static final long serialVersionUID = -2173942461694544160L;

	@SuppressWarnings("unchecked")
	public Collection<AbstractPerception<?>> getAgentPerceptions(
			Physics physics, State context) {
		try {
			return (Collection<AbstractPerception<?>>) physics
					.getClass()
					.getMethod(ReflectiveMethodStore.GETAGENTPERCEPTIONS,
							this.getClass(), State.class)
					.invoke(physics, this, context);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

	public abstract Set<AbstractPerception<?>> getOtherPerceptions(
			Physics physics, State context);

	public abstract boolean perform(Physics physics, State context);

	public abstract boolean isPossible(Physics physics, State context);

	public abstract boolean verify(Physics physics, State context);

}