package uk.ac.rhul.cs.dice.starworlds.appearances;

import java.io.Serializable;

import uk.ac.rhul.cs.dice.starworlds.entities.Entity;
import uk.ac.rhul.cs.dice.starworlds.utils.Identifiable;

/**
 * The interface for appearances. An {@link Appearance} should be
 * {@link Serializable}. <br/>
 * <br/>
 * 
 * Known implementations: {@link AbstractAppearance}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public interface Appearance extends Serializable, Identifiable {
    /**
     * Returns a {@link String} representation of the {@link Appearance}.
     * 
     * @return a {@link String} representation of the {@link Appearance}.
     */
    public abstract String represent();

    /**
     * Returns the {@link String} id of the {@link Entity} the {@link Appearance}
     * refers to.
     * 
     * @return the {@link String} id of the {@link Entity} the {@link Appearance}
     *         refers to.
     */
    public abstract String getId();

    /**
     * Sets the {@link String} id of the {@link Entity} the {@link Appearance}
     * refers to.
     * 
     * @param id
     *            : the {@link String} id of the {@link Entity} the
     *            {@link Appearance} refers to.
     */
    public abstract void setId(String id);
}