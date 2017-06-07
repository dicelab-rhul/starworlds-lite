package uk.ac.rhul.cs.dice.starworlds.perception;

import java.io.Serializable;
import java.lang.reflect.Constructor;

/**
 * A concrete implementation of {@link Perception}. Specifically for
 * {@link Perception}s that will be sent over network. This class ensures that
 * the content of the {@link Perception} is {@link Serializable}. An
 * {@link Abstract} may be converted to a {@link SerializablePerception} using
 * the static method
 * {@link SerializablePerception#convertToSerializable(AbstractPerception)} as
 * long as the content of the given {@link AbstractPerception} is
 * {@link Serializable}. After converting to a {@link SerializablePerception}
 * one may wish to convert back to the original {@link Perception} class for
 * later type processing. The old class of a converted
 * {@link AbstractPerception} is kept for this reason.
 * 
 * @author Ben
 *
 * @param <T>
 */

public class SerializablePerception<T extends Serializable> extends
		DefaultPerception<T> {

	private Class<?> oldclass = null;

	public SerializablePerception(T content, Class<?> oldclass) {
		super(content);
		this.oldclass = oldclass;
	}

	public SerializablePerception(T content) {
		super(content);
	}

	public static boolean isSerializable(AbstractPerception<?> perception) {
		return Serializable.class.isAssignableFrom(perception.getClass());
	}

	/**
	 * Checks if the given {@link AbstractPerception} has {@link Serializable}
	 * content. This may be done when type checking wildcard
	 * {@link AbstractPerception}s for later conversion to
	 * {@link SerializablePerception}.
	 * 
	 * @param perception
	 *            to check
	 * @return true if the content is {@link Serializable}, false otherwise
	 */
	public static boolean isContentSerializable(AbstractPerception<?> perception) {
		if (perception.getPerception() == null) {
			return true;
		}
		return Serializable.class.isAssignableFrom(perception.getPerception()
				.getClass());
	}

	/**
	 * Converts the given {@link SerializablePerception} to the class it was
	 * before it was converted to a {@link SerializablePerception}, if it was
	 * never converted the given perception is returned with no alterations.
	 * 
	 * @param perception
	 *            to convert back
	 * @return
	 */
	public static AbstractPerception<?> convertBack(
			SerializablePerception<?> perception) {
		if (perception.oldclass == null) {
			return perception;
		}
		try {
			Constructor<?>[] constructors = perception.oldclass
					.getDeclaredConstructors();
			return (AbstractPerception<?>) constructors[0]
					.newInstance(perception.getPerception());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Converts a given {@link AbstractPerception} that has {@link Serializable}
	 * content to a {@link SerializablePerception}.
	 * 
	 * @param perception
	 *            : to convert
	 * @return converted
	 */
	public static SerializablePerception<?> convertToSerializable(
			AbstractPerception<?> perception) {
		if (Serializable.class.isAssignableFrom(perception.getPerception()
				.getClass())) {
			try {
				Constructor<?> constructor = SerializablePerception.class
						.getDeclaredConstructor(Serializable.class, Class.class);
				return (SerializablePerception<?>) constructor.newInstance(
						perception.getPerception(), perception.getClass());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public Class<?> getOldclass() {
		return (Class<?>) oldclass;
	}
}
