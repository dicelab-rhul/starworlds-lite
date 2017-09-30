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

	protected AbstractAvatarMind<A> mind;
	protected HashMap<K, Class<? extends A>> actionclassmap;
	protected HashMap<K, Constructor<? extends A>> actionconstructormap;
	protected HashMap<K, Object[]> defaultargmap;

	/**
	 * Constructor.
	 * 
	 * @param mind
	 *            : to link
	 */
	public AbstractAvatarLink(M mind) {
		this.setMind(mind);
		mind.link();
		this.actionclassmap = new HashMap<>();
		this.actionconstructormap = new HashMap<>();
		this.defaultargmap = new HashMap<>();
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
	 * @throws AvatarLinkMappingException
	 *             if the key has no mapping
	 */
	public boolean decide(K key, Object... args) {
		if (key == null) {
			return mind.decide();
		}
		// create a new action
		Constructor<? extends A> ac = this.actionconstructormap.get(key);
		if (ac != null) {
			try {
				// maybe use default args
				if (args.length == 0) {
					if (defaultargmap.containsKey(key)) {
						return mind.decide(ac.newInstance(defaultargmap
								.get(key)));
					}
				}
				return mind.decide(ac.newInstance(args));
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
	public Class<? extends A> get(K key) {
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
	public boolean addMapping(K key, Class<? extends A> action,
			Class<?>... constructorArgs) throws NoSuchMethodException {
		if (constructorArgs == null || constructorArgs.length == 0) {
			this.actionconstructormap.put(key, action.getConstructor());
		} else {
			this.actionconstructormap.put(key,
					action.getConstructor(constructorArgs));
		}
		actionclassmap.put(key, action);
		return true;
	}

	/**
	 * Maps default arguments to a given key if there is a mapping for the given
	 * key. The default arguments are used if on
	 * {@link AbstractAvatarLink#decide(Object, Object...)} no additional
	 * arguments are given when they should have been.
	 * 
	 * @param key
	 * @param defaultargs
	 * @throws AvatarLinkMappingException
	 *             if the key has no mapping
	 */
	public void mapDefaultArguments(K key, Object... defaultargs) {
		if (actionclassmap.containsKey(key)) {
			// check the args
			Class<?>[] paramtypes = actionconstructormap.get(key)
					.getParameterTypes();
			if (defaultargs.length == paramtypes.length) {
				for (int i = 0; i < paramtypes.length; i++) {
					if (defaultargs[i] != null) {
						if (!paramtypes[i].isAssignableFrom(defaultargs[i]
								.getClass())) {
							throw new AvatarLinkMappingException(key
									+ ": defaultarg class: "
									+ defaultargs[i].getClass()
									+ " is not compatible with mapped type: "
									+ paramtypes[i]);
						}
					}
				}
				defaultargmap.put(key, defaultargs);
			} else {
				throw new AvatarLinkMappingException(key
						+ ": invalid defaultargs length = "
						+ defaultargs.length + " should be: "
						+ paramtypes.length);
			}
		} else {
			throw new AvatarLinkMappingException(key);
		}
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
