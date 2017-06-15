package uk.ac.rhul.cs.dice.starworlds.MVC;

import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.Universe;

/**
 * An abstract implementation of {@link ViewController}. Containing a reference
 * to the {@link Universe} (the Model). This should be sub-classed by a concrete
 * {@link ViewController} that mediates local Views. For remote views see
 * {@link ViewControllerServer}. </br> Known subclasses:
 * {@link ViewControllerServer}.
 * 
 * @author Ben
 *
 */
public abstract class AbstractViewController implements ViewController {

	private Universe universe;

	public AbstractViewController() {
	}

	public AbstractViewController(Universe universe) {
		super();
		this.universe = universe;
	}

	@Override
	public Universe getUniverse() {
		return this.universe;
	}

	@Override
	public void setUniverse(Universe universe) {
		this.universe = universe;
	}
}
