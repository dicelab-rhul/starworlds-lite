package uk.ac.rhul.cs.dice.gawl.interfaces.appearances;

import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Body;

/**
 * The {@link Appearance} of any entity. Contains only the name of the entity. <br/>
 * 
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class AbstractAppearance implements Appearance {

	private String name;

	/**
	 * Constructor with a {@link String} name.
	 * 
	 * @param name
	 *            : the name of the {@link Body}.
	 */
	public AbstractAppearance(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String bodyName) {
		this.name = bodyName;
	}
}