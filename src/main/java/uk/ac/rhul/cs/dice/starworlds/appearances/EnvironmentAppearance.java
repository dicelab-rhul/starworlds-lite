package uk.ac.rhul.cs.dice.starworlds.appearances;

import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.Environment;
import uk.ac.rhul.cs.dice.starworlds.utils.Utils;

public class EnvironmentAppearance extends AbstractAppearance {
    private static final long serialVersionUID = -8438158765085176583L;
    private boolean isRemote;
    private boolean isSimple;

    public EnvironmentAppearance(String id, Boolean isRemote, Boolean isSimple) {
	super(id);
	
	this.isRemote = isRemote;
	this.isSimple = isSimple;
    }

    public boolean isAtomic() {
	return this.isSimple;
    }

    public boolean isRemote() {
	return this.isRemote;
    }

    @Override
    public String toString() {
	return Environment.class.getSimpleName() + ":" + this.getId();
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + (this.isRemote ? 1231 : 1237);
	result = prime * result + (this.isSimple ? 1231 : 1237);
	
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if(Utils.equalsHelper(this, obj)) {
	    return true;
	}
	
	EnvironmentAppearance other = (EnvironmentAppearance) obj;
	
	if(other == null) {
	    return false;
	}
	
	return this.isRemote == other.isRemote && this.isSimple == other.isSimple;
    }
}