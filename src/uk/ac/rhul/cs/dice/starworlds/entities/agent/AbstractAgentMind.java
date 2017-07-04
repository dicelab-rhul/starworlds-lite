package uk.ac.rhul.cs.dice.starworlds.entities.agent;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.AbstractSensor;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.concrete.PhysicalActuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.concrete.SpeechActuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.concrete.DefaultAgentMind;
import uk.ac.rhul.cs.dice.starworlds.parser.DefaultConstructorStore.DefaultConstructor;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;

/**
 * The abstract implementation of {@link Mind}. This mind houses useful method
 * for any concrete implementation of the a {@link Mind}. Including methods to
 * access information about the agent this {@link Mind} resides in, methods for
 * performing {@link Action}s - speak, sense, do. See {@link SpeechActuator},
 * {@link AbstractSensor}, {@link PhysicalActuator}. </br> When a concrete
 * implementation of a {@link Mind} is in its {@link Mind#execute(Object...)}
 * procedure it should call either {@link AbstractAgentMind#d}
 * //TODO If this method is not called with the actions, the agent will be idle
 * in the current cycle.
 * 
 * 
 * </br> Known direct subclasses: {@link DefaultAgentMind}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class AbstractAgentMind extends
		AbstractMind<Perception<?>, Action> {

	@DefaultConstructor
	public AbstractAgentMind() {
	}

	@Override
	public Action cycle(Collection<Perception<?>> perceptions) {
		Perception<?> mindPerception = this.perceive(perceptions);
		Action action = this.decide(mindPerception);
		return this.execute(action);
	}

	@Override
	public abstract Perception<?> perceive(Collection<Perception<?>> perceptions);

	@Override
	public abstract Action decide(Perception<?> perception);

	@Override
	public abstract Action execute(Action action);

}