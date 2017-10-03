package uk.ac.rhul.cs.dice.starworlds.appearances;

import uk.ac.rhul.cs.dice.starworlds.entities.PhysicalBody;
import uk.ac.rhul.cs.dice.starworlds.initialisation.IDFactory;
import uk.ac.rhul.cs.dice.starworlds.utils.Utils;

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
	result = prime * result + ((this.body == null) ? 0 : this.body.hashCode());
	
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (!Utils.equalsHelper(this, obj)) {
	    return false;
	}
	
	PhysicalBodyAppearance other = (PhysicalBodyAppearance) obj;
	
	if(other == null) {
	    return false;
	}
	
	if(Utils.checkBothNull(this.body, other.body)) {
	    return true;
	}
	else if(Utils.checkBothNonNull(this.body, other.body)){
	    return this.body.isAssignableFrom(other.body) && other.body.isAssignableFrom(this.body);
	}
	else {
	    return false;
	}
    }
}