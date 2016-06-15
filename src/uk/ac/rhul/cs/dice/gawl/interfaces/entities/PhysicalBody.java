package uk.ac.rhul.cs.dice.gawl.interfaces.entities;

import uk.ac.rhul.cs.dice.gawl.interfaces.appearances.Appearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObserver;

/**
 * The most generic class for physical bodies implementing {@link Body}. It extends {@link CustomObservable}
 * and can become a {@link CustomObserver}.
 * It has a {@link String} id and an {@link Appearance}.<br/><br/>
 * 
 * Known direct subclasses: {@link ActiveBody}, {@link PassiveBody}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class PhysicalBody extends CustomObservable implements Body, CustomObserver {
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