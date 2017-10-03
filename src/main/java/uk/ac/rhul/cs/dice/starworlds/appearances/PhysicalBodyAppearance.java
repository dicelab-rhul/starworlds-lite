package uk.ac.rhul.cs.dice.starworlds.appearances;

import uk.ac.rhul.cs.dice.starworlds.entities.PhysicalBody;
import uk.ac.rhul.cs.dice.starworlds.initialisation.IDFactory;

public class PhysicalBodyAppearance extends AbstractAppearance {
    private static final long serialVersionUID = -6276811265235520867L;
    private Class<? extends PhysicalBody> body;

    public PhysicalBodyAppearance(Class<? extends PhysicalBody> body, String id) {
	super(id);
	this.body = body;
    }

    public PhysicalBodyAppearance(Class<? extends PhysicalBody> body) {
	super(IDFactory.getInstance().getNewID());
	this.body = body;
    }

    public Class<? extends PhysicalBody> getBody() {
	return this.body;
    }

    @Override
    public String represent() {
	return super.represent() + REPSEP + this.body.getSimpleName();
    }

    @Override
    public String toString() {
	return this.represent();
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + ((body == null) ? 0 : body.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (!super.equals(obj))
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	PhysicalBodyAppearance other = (PhysicalBodyAppearance) obj;
	if (body == null) {
	    if (other.body != null)
		return false;
	} else if (!body.equals(other.body))
	    return false;
	return true;
    }
}