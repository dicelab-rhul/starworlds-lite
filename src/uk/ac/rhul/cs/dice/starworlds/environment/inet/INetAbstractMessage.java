package uk.ac.rhul.cs.dice.starworlds.environment.inet;

import java.io.Serializable;

import uk.ac.rhul.cs.dice.starworlds.environment.base.AbstractMessage;

public abstract class INetAbstractMessage<T extends Serializable> extends
		AbstractMessage<T> implements Serializable {

	private static final long serialVersionUID = -7398687670848928068L;

	public INetAbstractMessage(T payload) {
		super(payload);
	}

}
