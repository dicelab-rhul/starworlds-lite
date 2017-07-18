package uk.ac.rhul.cs.dice.starworlds.actions.environmental;

import java.util.Arrays;

import uk.ac.rhul.cs.dice.starworlds.environment.subscriber.AbstractSubscriber.SensiblePerception;
import uk.ac.rhul.cs.dice.starworlds.perception.ActivePerception;

/**
 * A subclass of {@link AbstractEnvironmentalAction} representing sensing
 * actions.<br/>
 * <br/>
 * 
 * Known direct subclasses: none.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public class SensingAction extends AbstractEnvironmentalAction {

	private static final long serialVersionUID = 4695622233320564044L;

	@SensiblePerception
	public static final Class<?> POSSIBLEPERCEPTION = ActivePerception.class;

	private String[] keys;

	public SensingAction(String... keys) {
		this.keys = (keys != null) ? keys : new String[] {};
	}

	public String[] getKeys() {
		return keys;
	}

	@Override
	public String toString() {
		return super.toString() + " : " + Arrays.toString(this.keys);
	}
}