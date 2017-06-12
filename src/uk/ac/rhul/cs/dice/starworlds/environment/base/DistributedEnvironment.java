package uk.ac.rhul.cs.dice.starworlds.environment.base;

import java.io.IOException;
import java.io.Serializable;
import java.net.SocketAddress;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.Appearance;
import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.AbstractSensor;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Sensor;
import uk.ac.rhul.cs.dice.starworlds.environment.AbstractEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.State;
import uk.ac.rhul.cs.dice.starworlds.environment.inet.INetDefaultMessage;
import uk.ac.rhul.cs.dice.starworlds.environment.inet.INetDefaultServer;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.Physics;
import uk.ac.rhul.cs.dice.starworlds.environment.subscriber.AbstractSubscriber;
import uk.ac.rhul.cs.dice.starworlds.environment.subscriber.INetSubscriber;
import uk.ac.rhul.cs.dice.starworlds.environment.subscriber.Subscriber;
import uk.ac.rhul.cs.dice.starworlds.perception.AbstractPerception;
import uk.ac.rhul.cs.dice.starworlds.perception.SerializablePerception;
import uk.ac.rhul.cs.dice.starworlds.utils.Pair;
import uk.ac.rhul.cs.dice.starworlds.utils.inet.INetServer;

public abstract class DistributedEnvironment extends AbstractEnvironment
		implements Observer {

	protected static final String SUBSCRIBE = "SUBSCRIBE";
	protected Map<String, INetCommand> inetCommands;

	public DistributedEnvironment(
			State state,
			Physics physics,
			Boolean bounded,
			Appearance appearance,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions,
			INetServer server) {
		super(new INetSubscriber(), state, physics, bounded, appearance,
				possibleActions);
		init(server);
	}

	public DistributedEnvironment(
			State state,
			Physics physics,
			Boolean bounded,
			Appearance appearance,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions,
			int port) {
		super(new INetSubscriber(), state, physics, bounded, appearance,
				possibleActions);
		init(new INetDefaultServer(port));
	}
	
	

	private void init(INetServer server) {
		this.server = server;
		this.server.addObserver(this);
		inetCommands = new HashMap<>();
		inetCommands.put(SUBSCRIBE, new INetCommandSubscribe());
	}
	
	public void addSuperEnvironment(String host, int port);

	public void addSubEnvironment(String host, int port);

	public void addNeighbouringEnvironment(String host, int port);

	public void addInetCommand(String key, INetCommand command) {
		inetCommands.put(key, command);
	}

	/**
	 * Method that will be called if any valid data is received over the
	 * network.
	 */
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("UPDATE");
		// TODO do we need to do this check? the update method in INetServer
		// should never be overridden
		if (Pair.class.isAssignableFrom(arg.getClass())) {
			Pair<?, ?> pair = (Pair<?, ?>) arg;
			if (INetDefaultMessage.class.isAssignableFrom(pair.getSecond()
					.getClass())) {
				Pair<String, Serializable> message = ((INetDefaultMessage) pair
						.getSecond()).getPayload();
				INetCommand function;
				if ((function = inetCommands.get(message.getFirst())) != null) {
					function.execute(message.getSecond(),
							(SocketAddress) pair.getFirst());
				}
			}
			receivedmessage((SocketAddress) pair.getFirst(), arg);
		}
	}

	/**
	 * The method that will be called when any valid input is received that
	 * cannot be handled by default. This is, any input that does not have a
	 * corresponding command mapping or any input that should be handled
	 * differently. This should be application dependent.
	 * 
	 * @param address
	 *            of the sender
	 * @param arg
	 *            message received
	 */
	public abstract void receivedmessage(SocketAddress address, Object arg);

	/**
	 * Attempts to connect to another {@link DistributedEnvironment}. //TODO are
	 * they the same environment or not?
	 * 
	 * @param host
	 * @param port
	 */
	public void connectToEnvironment(String host, int port) {
		SocketAddress addr = null;
		try {
			addr = server.connect(host, port);
			remoteSubscribe(addr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method will send a subscription message that contains all of the
	 * {@link ActiveBody}s that have subscribed to this
	 * {@link DistributedEnvironment} via its {@link AbstractSubscriber}. This
	 * method will be called by
	 * {@link DistributedEnvironment#connectToEnvironment(String, int)} method
	 * immediately after connection is made. If this method does not have the
	 * desired behaviour, override it in a subclass. Generally after a
	 * subscription message, the receiving environment will send thier own
	 * {@link ActiveBody} subscription information. However this is not
	 * guaranteed and is specified in {@link INetCommandSubscribe} (or the
	 * {@link INetCommand} mapped to the
	 * {@link DistributedEnvironment#SUBSCRIBE} key.
	 * 
	 * @param addr
	 *            of the {@link DistributedEnvironment} that has been connected
	 *            to
	 */
	public void remoteSubscribe(SocketAddress addr) {
		server.send(addr, new INetDefaultMessage(SUBSCRIBE, this
				.getSubscriber().getLocallySubscribedAgents()));
	}

	protected String[] representSensorsAsString(List<Sensor> sensors) {
		String[] result = new String[sensors.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = sensors.get(i).getId();
		}
		return result;
	}

	@Override
	protected INetSubscriber getSubscriber() {
		return (INetSubscriber) super.subscriber;
	}

	public void notifyRemote(AbstractEnvironmentalAction action, String id,
			Collection<AbstractPerception<?>> perceptions, State context) {
		// TODO Auto-generated method stub
		System.out.println("NOTIFY REMOTE: " + id);
		for (AbstractPerception<?> p : perceptions) {
			AbstractPerception<?> s = getSerializablePerception(p);
			System.out.println(s);
		}
	}

	private AbstractPerception<?> getSerializablePerception(
			AbstractPerception<?> p) {
		if (SerializablePerception.class.isAssignableFrom(p.getClass())
				|| SerializablePerception.isSerializable(p)) {
			return p;
		} else if (SerializablePerception.isContentSerializable(p)) {
			return SerializablePerception.convertToSerializable(p);
		} else {
			System.err
					.println("The perception: "
							+ p
							+ System.lineSeparator()
							+ "and its content are not serializable. It cannot be sent over network.");
			return null;
		}
	}

	protected boolean checkPerceivable(String sensor,
			AbstractPerception<?> perception, State context) {
		return true; // TODO, we dont have an instance of the sensor this may
						// have to be done remotely
	}

	@Override
	protected void notifySensor(AbstractSensor sensor,
			AbstractPerception<?> perception) {
		// TODO Auto-generated method stub

	}

	/**
	 * A concrete {@link INetCommand} that will execute a subscribe. It will
	 * take any objects given that are subscribable and subscribe them to this
	 * {@link DistributedEnvironment}s {@link Subscriber}. TODO
	 * 
	 * @author Ben
	 *
	 */
	protected class INetCommandSubscribe extends INetCommand {
		@SuppressWarnings("unchecked")
		@Override
		public void execute(Object obj, SocketAddress receivedBy) {
			System.out.println("SUBSCRIBE WITH: " + obj);
			if (Map.class.isAssignableFrom(obj.getClass())) {
				Map<?, ?> map = (Map<?, ?>) obj;
				map.forEach((Object o1, Object o2) -> {
					// TODO type safety
					DistributedEnvironment.this.getSubscriber().addRemoteAgent(
							(String) o1,
							(Set<Class<? extends AbstractSensor>>) o2,
							receivedBy);
				});
				System.out.println(DistributedEnvironment.this.getSubscriber()
						.getRemoteAgents());
			}
		}
	}

	/**
	 * The abstract class representing some command that may be sent over
	 * network by any other environment. Typically a will be contained within a
	 * {@link INetDefaultMessage} as the {@link String} parameter. The
	 * {@link Serializable} content of the message will be given as the
	 * arguments to the {@link INetCommand#execute(Object, SocketAddress)}
	 * method on receipt of the message. All {@link INetCommand}s should be
	 * added at instantiation of a {@link DistributedEnvironment} using the
	 * {@link DistributedEnvironment#addInetCommand(String, INetCommand)}
	 * method. </br> Known subclasses: {@link INetCommandSubscribe}
	 * 
	 * @author Ben
	 *
	 */
	protected abstract class INetCommand {
		public abstract void execute(Object obj, SocketAddress receivedBy);

		@Override
		public String toString() {
			return this.getClass().getSimpleName();
		}
	}
}
