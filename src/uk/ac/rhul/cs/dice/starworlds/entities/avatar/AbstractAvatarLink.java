package uk.ac.rhul.cs.dice.starworlds.entities.avatar;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;

/**
 * This class should be extended by any class that wishes to link a View to an
 * Avatar.
 * 
 * @author Ben
 *
 * @param <K>
 * @param <A>
 * @param <M>
 */
public abstract class AbstractAvatarLink<K, A extends Action, M extends AbstractAvatarMind<A>> {

	private AbstractAvatarMind<A> mind;
	private HashMap<K, Class<A>> actionclassmap;
	private HashMap<K, Constructor<A>> actionconstructormap;

	public AbstractAvatarLink(M mind) {
		this.setMind(mind);
		this.actionclassmap = new HashMap<>();
		this.actionconstructormap = new HashMap<>();
	}

	public Class<A> get(K key) {
		return actionclassmap.get(key);
	}

	public boolean addMapping(K key, Class<A> value,
			Class<?>... constructorArgs) throws NoSuchMethodException,
			SecurityException {
		if (actionclassmap.containsKey(key)) {
			return false;
		}
		if (constructorArgs.length != 0) {
			this.actionconstructormap.put(key,
					value.getConstructor(constructorArgs));
		} else {
			this.actionconstructormap.put(key, value.getConstructor());
		}
		actionclassmap.put(key, value);
		return true;
	}

	public void removeMapping(K key) {
		actionclassmap.remove(key);
		actionconstructormap.remove(key);
	}

	public boolean containsKey(K key) {
		return actionclassmap.containsKey(key);
	}

	public boolean containsMapping(A value) {
		return actionclassmap.containsValue(value.getClass());
	}

	public void clearMappings() {
		actionclassmap.clear();
		actionconstructormap.clear();
	}

	public AbstractAvatarMind<A> getMind() {
		return mind;
	}

	public void setMind(AbstractAvatarMind<A> mind) {
		this.mind = mind;
	}
}
