package uk.ac.rhul.cs.dice.starworlds.actions.environmental;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.environment.ambient.Ambient;
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
	public final Collection<AbstractPerception<?>> getAgentPerceptions(
			Physics physics, Ambient context) throws Exception {
		return (Collection<AbstractPerception<?>>) ReflectiveMethodStore
				.getActionMethod(this,
						ReflectiveMethodStore.GETAGENTPERCEPTIONS).invoke(
						physics, this, context);
	}

	@SuppressWarnings("unchecked")
	public final Collection<AbstractPerception<?>> getOtherPerceptions(
			Physics physics, Ambient context) throws Exception {
		return (Collection<AbstractPerception<?>>) ReflectiveMethodStore
				.getActionMethod(this,
						ReflectiveMethodStore.GETOTHERPERCEPTIONS).invoke(
						physics, this, context);
	}

	public final boolean perform(Physics physics, Ambient context)
			throws Exception {
		return (boolean) ReflectiveMethodStore.getActionMethod(this,
				ReflectiveMethodStore.PERFORM).invoke(physics, this, context);
	}

	public final boolean isPossible(Physics physics, Ambient context)
			throws Exception {
		return (boolean) ReflectiveMethodStore.getActionMethod(this,
				ReflectiveMethodStore.ISPOSSIBLE)
				.invoke(physics, this, context);
	}

	public final boolean verify(Physics physics, Ambient context)
			throws Exception {
		return (boolean) ReflectiveMethodStore.getActionMethod(this,
				ReflectiveMethodStore.VERIFY).invoke(physics, this, context);
	}

}