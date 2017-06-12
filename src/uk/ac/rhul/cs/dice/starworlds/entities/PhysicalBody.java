package uk.ac.rhul.cs.dice.starworlds.entities;

import java.util.Objects;

import uk.ac.rhul.cs.dice.starworlds.appearances.Appearance;
import uk.ac.rhul.cs.dice.starworlds.appearances.PhysicalBodyAppearance;
import uk.ac.rhul.cs.dice.starworlds.initialisation.IDFactory;

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

	private String id;
	private Appearance appearance;

	/**
	 * Constructor.
	 */
	public PhysicalBody() {
		this.id = IDFactory.getInstance().getNewID();
		this.appearance = new PhysicalBodyAppearance(this);
	}

	/**
	 * Constructor with an external {@link Appearance}.
	 * 
	 * @param appearance
	 *            : the external {@link Appearance} of the {@link PhysicalBody}.
	 */
	public PhysicalBody(Appearance appearance) {
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
	 * Sets the external {@link Appearance} of the {@link PhysicalBody}.
	 * 
	 * @param externalAppearance
	 *            : the external {@link Appearance} of the {@link PhysicalBody}.
	 */
	public void setExternalAppearance(Appearance externalAppearance) {
		this.appearance = externalAppearance;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null) {
			if (obj instanceof PhysicalBody) {
				PhysicalBody b = (PhysicalBody) obj;
				return this.id.equals(b.getId());
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public void setId(String id) {
		this.id = (id != null) ? id.toString() : "NULLID";
		if (this.appearance != null) {
			this.appearance.setName(this.id);
		}
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public String toString() {
		return this.appearance.toString();
	}
}