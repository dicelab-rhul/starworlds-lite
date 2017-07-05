package uk.ac.rhul.cs.dice.starworlds.entities.avatar.link;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.entities.avatar.AbstractAvatarMind;

/**
 * A concrete implementation of {@link AbstractAvatarMind} that uses the java
 * {@link KeyEvent} Integer Key Codes as the input Key in the Key-{@link Action}
 * mapping. Typically this class will be used in conjunction with a
 * {@link KeyListener} in a view. The
 * {@link KeyboardAvatarLink#decide(Object, Object...)} method will then be
 * called by the {@link KeyListener} with the key code, which may be obtained
 * from the {@link KeyEvent} using {@link KeyEvent#getKeyCode()}.
 * 
 * @author Ben Wilkins
 *
 * @param <A>
 *            : type parameter for the {@link Action} in the Key-{@link Action}
 *            mapping.
 * @param <M>
 *            : type parameter for the {@link AbstractAvatarMind AvatarMind}
 *            that is to be linked to a view.
 */
public class KeyboardAvatarLink<A extends Action, M extends AbstractAvatarMind<A>>
		extends AbstractAvatarLink<Integer, A, M> {

	public KeyboardAvatarLink(M mind) {
		super(mind);
	}

}
