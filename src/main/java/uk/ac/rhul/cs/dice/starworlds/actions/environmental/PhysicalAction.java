package uk.ac.rhul.cs.dice.starworlds.actions.environmental;

import java.util.Collection;
import java.util.stream.Collectors;

import uk.ac.rhul.cs.dice.starworlds.environment.ambient.Ambient;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.Physics;
import uk.ac.rhul.cs.dice.starworlds.exceptions.StarWorldsException;
import uk.ac.rhul.cs.dice.starworlds.initialisation.ReflectiveMethodStore;
import uk.ac.rhul.cs.dice.starworlds.perception.AbstractPerception;

/**
 * TODO
 * 
 * Known direct subclasses: none.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class PhysicalAction extends AbstractEnvironmentalAction {

    private static final long serialVersionUID = -2173942461694544160L;

    public final Collection<Object> getAgentPerceptions(Physics physics, Ambient context) throws StarWorldsException {
	try {
	    Object temp = ReflectiveMethodStore.getActionMethod(this, ReflectiveMethodStore.GETAGENTPERCEPTIONS).invoke(physics, this, context);
	    
	    if(temp != null && temp instanceof Collection<?>) {
		return ((Collection<?>) temp).stream().filter(elm -> AbstractPerception.class.isAssignableFrom(elm.getClass())).map(elm -> (AbstractPerception<?>) elm).collect(Collectors.toList());
	    }
	    else {
		throw new StarWorldsException("Bad returned collection!");
	    }
	}
	catch(Exception e) {
	    throw new StarWorldsException(e);
	}
    }

    public final Collection<Object> getOtherPerceptions(Physics physics, Ambient context) throws StarWorldsException {
	try {
	    Object temp = ReflectiveMethodStore.getActionMethod(this, ReflectiveMethodStore.GETOTHERPERCEPTIONS).invoke(physics, this, context);
	    
	    if(temp != null && temp instanceof Collection<?>) {
		return ((Collection<?>) temp).stream().filter(elm -> AbstractPerception.class.isAssignableFrom(elm.getClass())).map(elm -> (AbstractPerception<?>) elm).collect(Collectors.toList());
	    }
	    else {
		throw new StarWorldsException("Bad returned collection!");
	    }
	}
	catch(Exception e) {
	    throw new StarWorldsException(e);
	}
    }

    public final boolean perform(Physics physics, Ambient context) throws StarWorldsException {
	try {
	    return (boolean) ReflectiveMethodStore.getActionMethod(this, ReflectiveMethodStore.PERFORM).invoke(physics, this, context);
	}
	catch(Exception e) {
	    throw new StarWorldsException(e);
	}
    }

    public final boolean isPossible(Physics physics, Ambient context) throws StarWorldsException {
	try {
	    return (boolean) ReflectiveMethodStore.getActionMethod(this, ReflectiveMethodStore.ISPOSSIBLE).invoke(physics, this, context);
	}
	catch(Exception e) {
	    throw new StarWorldsException(e);
	}
    }

    public final boolean verify(Physics physics, Ambient context) throws StarWorldsException  {
	try {
	    return (boolean) ReflectiveMethodStore.getActionMethod(this, ReflectiveMethodStore.VERIFY).invoke(physics, this, context);
	}
	catch(Exception e) {
	    throw new StarWorldsException(e);
	}
    }
}