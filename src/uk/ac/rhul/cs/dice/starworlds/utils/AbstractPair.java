package uk.ac.rhul.cs.dice.starworlds.utils;

import java.io.Serializable;

public abstract class AbstractPair<T1, T2> implements Serializable {

	private static final long serialVersionUID = -6023533208083775052L;

	private T1 first;
	private T2 second;

	public AbstractPair(T1 first, T2 second) {
		this.first = first;
		this.second = second;
	}

	public T1 getFirst() {
		return this.first;
	}

	public T2 getSecond() {
		return this.second;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.first == null) ? 0 : this.first.hashCode());
		result = prime * result
				+ ((this.second == null) ? 0 : this.second.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!Utils.equalsHelper(this, obj)) {
			return false;
		} else {
			return checkValues(obj);
		}
	}

	private boolean checkValues(Object obj) {
		AbstractPair<?, ?> other = (AbstractPair<?, ?>) obj;

		if (other == null) {
			return false;
		}

		return checkFirst(other) && checkSecond(other);
	}

	private boolean checkFirst(AbstractPair<?, ?> other) {
		if (Utils.checkBothNull(this.first, other.getFirst())) {
			return true;
		} else {
			return this.first.equals(other.getFirst());
		}
	}

	private boolean checkSecond(AbstractPair<?, ?> other) {
		if (Utils.checkBothNull(this.second, other.getSecond())) {
			return true;
		} else {
			return this.second.equals(other.getSecond());
		}
	}

	public boolean checkClasses(Class<?> firstClass, Class<?> secondClass) {
		return firstClass.isAssignableFrom(this.first.getClass())
				&& secondClass.isAssignableFrom(this.second.getClass());
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "<" + this.first + ","
				+ this.second + ">";
	}
}