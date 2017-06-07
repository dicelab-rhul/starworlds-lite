package uk.ac.rhul.cs.dice.starworlds.appearances;

public class EnvironmentAppearance extends AbstractAppearance {

	private static final long serialVersionUID = -8438158765085176583L;
	
	private boolean isRemote;
	private boolean isSimple;

	public EnvironmentAppearance(String name, Boolean isRemote, Boolean isSimple) {
		super(name);
	}

	public boolean isSimple() {
		return isSimple;
	}

	public boolean isRemote() {
		return isRemote;
	}
}
