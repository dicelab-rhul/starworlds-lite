package uk.ac.rhul.cs.dice.starworlds.utils;

/**
 * An interface that should be implemented by any class whose instances should
 * be indentifiable.
 * 
 * @author Ben
 *
 */
public interface Identifiable {

	/**
	 * Returns the unique ID of the entity.
	 * 
	 * @return a {@link String} representing the unique ID of the entity.
	 */
	public String getId();

	/**
	 * Sets a unique ID for the entity.
	 * 
	 * @param id
	 *            : a {@link String} representing the unique ID.
	 */
	public void setId(String id);

}
