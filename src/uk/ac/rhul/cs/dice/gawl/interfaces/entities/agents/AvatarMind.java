package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents;

import java.util.Random;

/**
 * A subclass of {@link AbstractAgentMind} designed for {@link Avatar} agents.<br/><br/>
 * 
 * Known direct subclasses: none.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class AvatarMind extends AbstractAgentMind {

	public AvatarMind(Random rng, String bodyId) {
		super(rng, bodyId);
	}
	
	public AvatarMind(String bodyId) {
		super(bodyId);
	}
}