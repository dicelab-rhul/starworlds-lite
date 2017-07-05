package uk.ac.rhul.cs.dice.starworlds.entities.avatar.link;

import java.util.Arrays;

public class AvatarLinkMappingException extends RuntimeException {

	private static final long serialVersionUID = 4024049934707068026L;

	public AvatarLinkMappingException(Object key) {
		super("No mapping exists for key: " + key);
	}

	public AvatarLinkMappingException(Object key, Class<?> action,
			Object... args) {
		super("No mapping exists for action: " + action
				+ " with constructor parameters: " + Arrays.toString(args));
	}
}
