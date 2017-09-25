package uk.ac.rhul.cs.dice.starworlds.entities;

import java.util.Objects;

import uk.ac.rhul.cs.dice.starworlds.appearances.Appearance;
import uk.ac.rhul.cs.dice.starworlds.appearances.PhysicalBodyAppearance;

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
		return (PhysicalBodyAppearance) this.appearance;
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
	public boolean equals(Object obj) {
		if (obj != null) {
			if (obj instanceof PhysicalBody) {
				PhysicalBody b = (PhysicalBody) obj;
				return this.appearance.getId().equals(b.getId());
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(appearance.getId());
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
}