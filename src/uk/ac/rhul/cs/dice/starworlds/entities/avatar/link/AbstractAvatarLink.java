package uk.ac.rhul.cs.dice.starworlds.entities.avatar.link;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import uk.ac.rhul.cs.dice.starworlds.MVC.ViewController;
import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.entities.avatar.AbstractAvatarMind;
import uk.ac.rhul.cs.dice.starworlds.entities.avatar.Avatar;

/**
 * This class should be extended by any class that wishes to link a View to an
 * {@link Avatar}. This class creates a mapping between keys of type <b> K </b>
 * and {@link Action}s of type <b> A</b>. </br> This mapping determines the
 * {@link Action}s that the {@link AbstractAvatarMind} will perform when the a
 * user provides some input. Each Key should be some unique value representing
 * the input of the User. A simple example see {@link KeyboardAvatarLink}. </br>
 * </br> When an {@link Action} is decided upon by the user, the
 * {@link AbstractAvatarLink#decide(Object, Object...)
 * AbstractAvatarLink#decide(Key, Object...)} method should be called (usually
 * by a {@link ViewController} or other listener responsible for handling user
 * input). Arguments may be given to this method, that will be used in the
 * creation of the decided {@link Action}. However, a mapping must have been
 * made prior to this, i.e. When calling
 * {@link AbstractAvatarLink#addMapping(Object, Class, Class...)}. The types of
 * the constructors parameters of the constructor must be provided, see
 * {@link Class#getConstructor(Class...)}. </br> Direct known subclass:
 * {@link KeyboardAvatarLink}.
 * 
 * @author Ben Wilkins
 *
 * @param <K>
 *            : type parameter for the key in the Key-{@link Action} mapping.
 * @param <A>
 *            : type parameter for the {@link Action} in the Key-{@link Action}
 *            mapping.
 * @param <M>
 *            : type parameter for the {@link AbstractAvatarMind AvatarMind}
 *            that is to be linked to a view.
 */
public abstract class AbstractAvatarLink<K, A extends Action, M extends AbstractAvatarMind<A>> {

	private AbstractAvatarMind<A> mind;
	private HashMap<K, Class<A>> actionclassmap;
	private HashMap<K, Constructor<A>> actionconstructormap;

	/**
	 * Constructor.
	 * 
	 * @param mind
	 *            : to link
	 */
	public AbstractAvatarLink(M mind) {
		this.setMind(mind);
		this.actionclassmap = new HashMap<>();
		this.actionconstructormap = new HashMap<>();
	}

	/**
	 * Call this method with (valid) input from the user, this will use the Key-
	 * {@link Action} mapping to signal the {@link Avatar} to attempt the mapped
	 * {@link Action}.
	 * 
	 * @param key
	 *            : user input
	 * @param args
	 *            : of the {@link Action} to be attempted by the {@link Avatar}
	 */
	public void decide(K key, Object... args) {
		// create a new action
		Constructor<A> ac = this.actionconstructormap.get(key);
		if (ac != null) {
			try {
				mind.decide(ac.newInstance(args));
			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException e) {
				throw new AvatarLinkMappingException(key,
						this.actionclassmap.get(key), args);
			}
		}
		throw new AvatarLinkMappingException(key);
	}

	/**
	 * Getter for a mapped {@link Action}.
	 * 
	 * @param key
	 * @return the mapped {@link Action}
	 */
	public Class<A> get(K key) {
		return actionclassmap.get(key);
	}

	/**
	 * Adds a new mapping between <b> key </b> and <b> action </b>. The empty
	 * constructor will be used if no <b> constructorArgs </b> are provided. The
	 * same {@link Action} may be mapped more than once (to a different <b>
	 * key</b>) as long as the constructorArgs are different.
	 * 
	 * @param key
	 *            : the Key to be mapped
	 * @param value
	 *            : the {@link Action} to be mapped
	 * @param constructorArgs
	 *            : An array of {@link Class}es that are used to identify which
	 *            {@link Action} constructor to use
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public boolean addMapping(K key, Class<A> action,
			Class<?>... constructorArgs) throws NoSuchMethodException,
			SecurityException {
		if (constructorArgs.length != 0) {
			this.actionconstructormap.put(key,
					action.getConstructor(constructorArgs));
		} else {
			this.actionconstructormap.put(key, action.getConstructor());
		}
		actionclassmap.put(key, action);
		return true;
	}

	/**
	 * Returns true if a mapping exists for the specified key.
	 * 
	 * @param key
	 * @return true if contains a mapping for the specified key, false otherwise
	 */
	public boolean containsKey(K key) {
		return actionclassmap.containsKey(key);
	}

	/**
	 * Returns true if a mapping exists for the specified {@link Action}.
	 * 
	 * @param action
	 * @return true if this map contains a mapping for the specified value,
	 *         false otherwise
	 */
	public boolean containsMapping(Class<A> action) {
		return actionclassmap.containsValue(action);
	}

	/**
	 * Removes all mappings.
	 */
	public void clearMappings() {
		actionclassmap.clear();
		actionconstructormap.clear();
	}

	/**
	 * Getter for the {@link AbstractAvatarMind AvatarMind}.
	 * 
	 * @return the {@link AbstractAvatarMind AvatarMind}
	 */
	public AbstractAvatarMind<A> getMind() {
		return mind;
	}

	/**
	 * Setter for the {@link AbstractAvatarMind AvatarMind}.
	 * 
	 * @param mind
	 *            : the {@link AbstractAvatarMind AvatarMind}
	 */
	public void setMind(AbstractAvatarMind<A> mind) {
		this.mind = mind;
	}
}
