package uk.ac.rhul.cs.dice.starworlds.entities;

import uk.ac.rhul.cs.dice.starworlds.appearances.Appearance;
import uk.ac.rhul.cs.dice.starworlds.appearances.PhysicalBodyAppearance;
import uk.ac.rhul.cs.dice.starworlds.utils.Utils;

/**
 * The most generic class for physical bodies implementing {@link Body}. It
 * extends {@link CustomObservable} and can become a {@link CustomObserver}. It
 * has a {@link String} id and an {@link Appearance}.<br/>
 * <br/>
 * 
 * Known direct subclasses: {@link ActiveBody}, {@link PassiveBody}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class PhysicalBody implements Body {
    private PhysicalBodyAppearance appearance;

    /**
     * Constructor.
     */
    public PhysicalBody() {
	this.appearance = new PhysicalBodyAppearance(this.getClass());
    }

    /**
     * Constructor with an external {@link Appearance}.
     * 
     * @param appearance
     *            : the external {@link Appearance} of the {@link PhysicalBody}.
     */
    public PhysicalBody(PhysicalBodyAppearance appearance) {
	this.appearance = appearance;
    }

    /**
     * Returns the {@link Appearance} of the {@link PhysicalBody}.
     * 
     * @return the {@link Appearance} of the {@link PhysicalBody}.
     */
    public PhysicalBodyAppearance getAppearance() {
	return this.appearance;
    }

    /**
     * Sets the {@link Appearance} of the {@link PhysicalBody}.
     * 
     * @param appearance
     *            : the {@link Appearance} of the {@link PhysicalBody}.
     */
    public void setAppearance(PhysicalBodyAppearance appearance) {
	this.appearance = appearance;
    }

    @Override
    public void setId(String id) {
	this.appearance.setId(id);
    }

    @Override
    public String getId() {
	return this.appearance.getId();
    }

    @Override
    public String toString() {
	return this.appearance.toString();
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((this.appearance == null) ? 0 : this.appearance.hashCode());
	
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (!Utils.equalsHelper(this, obj)) {
	    return false;
	}
	
	PhysicalBody other = (PhysicalBody) obj;
	
	if(other == null) {
	    return false;
	}
	
	if(Utils.checkNullMismatch(this.appearance, other.appearance)) {
	    return false;
	}
	else if(Utils.checkBothNull(this.appearance, other.appearance)){
	    return true;
	}
	else {
	    return this.appearance.equals(other.appearance);
	}
    }
}