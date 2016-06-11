package uk.ac.rhul.cs.dice.gawl.interfaces.entities;

import uk.ac.rhul.cs.dice.gawl.interfaces.common.PassiveBodyAppearance;

/**
 * A subclass of {@link PhysicalBody} which implements {@link ObjectInterface}.<br/><br/>
 * 
 * Known direct subclasses: //TODO
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Kostas Stathis
 *
 */
public abstract class PassiveBody extends PhysicalBody implements ObjectInterface {

	public PassiveBody(PassiveBodyAppearance appearance) {
		super(appearance);
	}

}