package uk.ac.rhul.cs.dice.starworlds.environment.concrete;

import java.io.Serializable;

import uk.ac.rhul.cs.dice.starworlds.appearances.Appearance;
import uk.ac.rhul.cs.dice.starworlds.appearances.EnvironmentAppearance;
import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.Environment;
import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.Message;
import uk.ac.rhul.cs.dice.starworlds.environment.inet.INetAbstractMessage;

/**
 * The class of {@link Message} that should be sent during initialisation of
 * connections between {@link Environment}s. This class holds only the
 * appearance of the sending {@link Environment}. TODO However it may be sub
 * classed to include additional initialisation information.
 * 
 * @author Benedict Wilkins
 */
public class InitialisationMessage extends INetAbstractMessage<Appearance>
		implements Serializable {
	private static final long serialVersionUID = 3308209205449776184L;

	public InitialisationMessage(EnvironmentAppearance payload) {
		super(payload);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " : " + this.getPayload();
	}
}