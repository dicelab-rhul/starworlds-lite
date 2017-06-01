package uk.ac.rhul.cs.dice.starworlds.appearances;

import uk.ac.rhul.cs.dice.starworlds.entities.Body;

/**
 * The {@link Appearance} of some entity. Contains only the name (which may be
 * the id) of the entity. <br/>
 * 
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class AbstractAppearance implements Appearance {

	private static final long serialVersionUID = 3285622766204723013L;

	protected static final String REPSEP = " ";

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

	@Override
	public String represent() {
		return this.getClass().getSimpleName() + REPSEP + this.name;
	}
}