package uk.ac.rhul.cs.dice.starworlds.appearances;

import uk.ac.rhul.cs.dice.starworlds.entities.PhysicalBody;

public class PhysicalBodyAppearance extends AbstractAppearance {

	private static final long serialVersionUID = -6276811265235520867L;

	private Class<? extends PhysicalBody> body;

	public PhysicalBodyAppearance(PhysicalBody body) {
		super(body.getId());
		this.body = body.getClass();
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
