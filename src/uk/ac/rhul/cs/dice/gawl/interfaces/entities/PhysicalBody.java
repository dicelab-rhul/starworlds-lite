package uk.ac.rhul.cs.dice.gawl.interfaces.entities;

import java.util.Observable;
import java.util.Observer;

/**
 * 
 * 
 * @author cloudstrife9999
 *
 */
public abstract class PhysicalBody extends Observable implements Body, Observer {
	private String id;
	
	@Override
	public void setId(Object id) {
		if(id instanceof String) {
			this.id = (String) id;
		}
		else {
			this.id = null;
		}
	}
	
	@Override
	public Object getId() {
		return this.id;
	}
}