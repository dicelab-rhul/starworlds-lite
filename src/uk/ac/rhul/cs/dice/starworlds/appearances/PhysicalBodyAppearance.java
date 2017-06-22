package uk.ac.rhul.cs.dice.starworlds.appearances;

import uk.ac.rhul.cs.dice.starworlds.entities.PhysicalBody;
import uk.ac.rhul.cs.dice.starworlds.initialisation.IDFactory;

public class PhysicalBodyAppearance extends AbstractAppearance {

	private static final long serialVersionUID = -6276811265235520867L;

	private Class<? extends PhysicalBody> body;

	public PhysicalBodyAppearance(String id, Class<? extends PhysicalBody> body) {
		super(id);
		this.body = body;
	}

	public PhysicalBodyAppearance(Class<? extends PhysicalBody> body) {
		super(IDFactory.getInstance().getNewID());
		this.body = body;
	}

	public Class<? extends PhysicalBody> getBody() {
		return body;
	}

	@Override
	public String represent() {
		return super.represent() + REPSEP + this.body.getSimpleName();
	}

	@Override
	public String toString() {
		return this.represent();
	}

}
