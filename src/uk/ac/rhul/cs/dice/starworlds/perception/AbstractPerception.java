package uk.ac.rhul.cs.dice.starworlds.perception;

public abstract class AbstractPerception<T> implements Perception<T> {

	private T content;

	public AbstractPerception(T content) {
		this.content = content;
	}

	@Override
	public T getPerception() {
		return content;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + ":" + content;
	}

}
