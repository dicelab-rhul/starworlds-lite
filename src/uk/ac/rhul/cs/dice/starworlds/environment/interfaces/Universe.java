package uk.ac.rhul.cs.dice.starworlds.environment.interfaces;

/**
 * The interface that should be implemented by any {@link Environment} that is
 * at the top of an {@link Environment} hierarchy, that is, it has no super
 * {@link Environment}. See {@link Simulator} for more details on starting a
 * simulation via a {@link Universe}.
 * 
 * @author Ben
 *
 */
public interface Universe extends Environment, Simulator {

}
