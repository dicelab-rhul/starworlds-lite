package uk.ac.rhul.cs.dice.gawl.interfaces.entities;

import java.util.Observable;
import java.util.Observer;

import uk.ac.rhul.cs.dice.gawl.interfaces.common.Appearance;

/**
 * The most generic class for physical bodies implementing {@link Body}. It is {@link Observable} and can become an {@link Observer}.
 * It has a {@link String} id and an {@link Appearance}.<br/><br/>
 * 
 * Known direct subclasses: {@link ActiveBody}, {@link PassiveBody}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Kostas Stathis
 *
 */
public abstract class PhysicalBody extends Observable implements Body, Observer {
	private String id;
	private Appearance externalAppearance;
	
	/**
	 * Constructor with an external {@link Appearance}.
	 * 
	 * @param externalAppearance : the external {@link Appearance} of the {@link PhysicalBody}.
	 */
	public PhysicalBody(Appearance externalAppearance) {
		this.externalAppearance = externalAppearance;
	}
	
	/**
	 * Returns the external {@link Appearance} of the {@link PhysicalBody}.
	 * 
	 * @return the external {@link Appearance} of the {@link PhysicalBody}.
	 */
	public Appearance getExternalAppearance() {
		return this.externalAppearance;
	}
	
	/**
	 * Sets the external {@link Appearance} of the {@link PhysicalBody}.
	 * 
	 * @param externalAppearance : the external {@link Appearance} of the {@link PhysicalBody}.
	 */
	public void setExternalAppearance(Appearance externalAppearance) {
		this.externalAppearance = externalAppearance;
	}
	
	@Override
	public void setId(Object id) {
		if(id instanceof String) {
			this.id = (String) id;
		}
		else {
			this.id = null;
		}
	}
	
	@Override
	public Object getId() {
		return this.id;
	}
}