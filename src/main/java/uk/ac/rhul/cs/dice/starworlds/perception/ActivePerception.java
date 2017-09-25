package uk.ac.rhul.cs.dice.starworlds.perception;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiConsumer;

public class ActivePerception extends AbstractPerception<Map<String, Object>> {

	private static final long serialVersionUID = 1439982634931162625L;

	public ActivePerception(Map<String, Object> content) {
		super(content);
	}

	public boolean containsKey(String result) {
		return content.containsKey(result);
	}

	public boolean containsValue(Object result) {
		return content.containsValue(result);
	}

	public Set<Entry<String, Object>> entrySet() {
		return content.entrySet();
	}

	public void forEach(BiConsumer<? super String, ? super Object> con) {
		content.forEach(con);
	}

	public Object get(String result) {
		return content.get(result);
	}

	public boolean isEmpty() {
		return content.isEmpty();
	}

	public int size() {
		return content.size();
	}

	public Collection<String> keys() {
		return content.keySet();
	}

	public Collection<Object> values() {
		return content.values();
	}
}
