package uk.ac.rhul.cs.dice.starworlds.entities;

/**
 * The interface for physical objects.<br/><br/>
 * 
 * Known direct sub-interfaces: {@link Body}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public interface Entity {
	
	/**
	 * Returns the unique ID of the entity.
	 * 
	 * @return a {@link String} representing the unique ID of the entity.
	 */
	public Object getId();
	
	/**
	 * Sets a unique ID for the entity.
	 * 
	 * @param id : a {@link String} representing the unique ID.
	 */
	public void setId(Object id);
}