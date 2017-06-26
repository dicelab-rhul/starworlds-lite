package uk.ac.rhul.cs.dice.starworlds.appearances;

import uk.ac.rhul.cs.dice.starworlds.entities.Body;
import uk.ac.rhul.cs.dice.starworlds.entities.Entity;
import uk.ac.rhul.cs.dice.starworlds.parser.DefaultConstructorStore.DefaultConstructor;
import uk.ac.rhul.cs.dice.starworlds.utils.Identifiable;

/**
 * The {@link Appearance} of some {@link Entity}. Contains only the id.
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

	private String id;

	/**
	 * Constructor.
	 */
	@DefaultConstructor
	public AbstractAppearance() {
	}

	/**
	 * Constructor.
	 * 
	 * @param id
	 *            : the id of the {@link Body}.
	 */
	public AbstractAppearance(String id) {
		this.setId(id);
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(String id) {
		this.id = (id != null) ? id : Identifiable.NULLID;
	}

	@Override
	public String represent() {
		return this.getClass().getSimpleName() + REPSEP + this.id;
	}

	@Override
	public String toString() {
		return represent();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractAppearance other = (AbstractAppearance) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}