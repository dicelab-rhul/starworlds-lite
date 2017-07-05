package uk.ac.rhul.cs.dice.starworlds.environment.interaction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import uk.ac.rhul.cs.dice.starworlds.appearances.EnvironmentAppearance;
import uk.ac.rhul.cs.dice.starworlds.environment.concrete.DefaultEnvironmentConnection;
import uk.ac.rhul.cs.dice.starworlds.environment.interaction.inet.INetDefaultServer;
import uk.ac.rhul.cs.dice.starworlds.environment.interaction.inet.INetEnvironmentConnection;
import uk.ac.rhul.cs.dice.starworlds.environment.interaction.inet.InitialisationMessage;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.AbstractConnectedEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.AbstractConnectedEnvironment.AmbientRelation;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.AbstractEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.Environment;
import uk.ac.rhul.cs.dice.starworlds.utils.inet.INetServer;
import uk.ac.rhul.cs.dice.starworlds.utils.inet.INetSlave;

public class EnvironmentConnectionManager implements Receiver, Observer {

	protected AbstractConnectedEnvironment localenvironment;
	protected Map<EnvironmentAppearance, AbstractEnvironmentConnection> subEnvironmentConnections;
	protected Map<EnvironmentAppearance, AbstractEnvironmentConnection> neighbouringEnvironmentConnections;
	protected AbstractEnvironmentConnection superEnvironmentConnection;

	protected INetServer server = null;
	protected Map<EnvironmentAppearance, Collection<Event<?>>> recievedMessages;
	protected volatile int numRemoteConnections = 0;
	protected volatile int expectedRemoteConnections = 0;

	/**
	 * Constructor specifically for mixed connections.
	 * 
	 * @param localenvironment
	 *            : the {@link Environment} that this
	 * @param localsubenvironments
	 *            : the local {@link Environment}s sub {@link Environment}s
	 * @param localneighbouringenvironments
	 *            : the local {@link Environment}s neighbouring
	 *            {@link Environment}s
	 * @param port
	 *            : the port that any remote {@link Environment} will try to
	 *            connect to
	 */
	public EnvironmentConnectionManager(
			AbstractConnectedEnvironment localenvironment, Integer port) {
		this.recievedMessages = new HashMap<>();
		this.subEnvironmentConnections = new HashMap<>();
		this.neighbouringEnvironmentConnections = new HashMap<>();
		this.localenvironment = localenvironment;
		if (port != null) {
			this.server = new INetDefaultServer(port);
			this.server.addObserver(this);
		}
	}

	public void initialiseLocalSubEnvironments(
			Collection<AbstractConnectedEnvironment> localsubenvironments,
			Collection<AbstractConnectedEnvironment> localneighbouringenvironments) {
		if (localsubenvironments != null) {
			localsubenvironments.forEach((AbstractConnectedEnvironment e) -> {
				addSubEnviroment(e);
			});
		}
		// TODO
		// if (localneighbouringenvironments != null) {
		// localneighbouringenvironments
		// .forEach((AbstractEnvironment e) -> this.state
		// .addNeighbouringEnvironment(e));
		// }
	}

	public void setExpectedRemoteConnections(int expectedRemoteConnections) {
		this.expectedRemoteConnections = expectedRemoteConnections;
	}

	public boolean expectingRemoteConnections() {
		System.out.println(this.localenvironment.getId()
				+ " EXPECTED CONNCETIONS: " + expectedRemoteConnections);
		return this.expectedRemoteConnections > 0;
	}

	public boolean reachedExpectedRemoteConnections() {
		return expectedRemoteConnections <= numRemoteConnections;
	}

	/**
	 * unused?
	 */
	public Map<EnvironmentAppearance, Collection<Event<?>>> flushMessages() {
		Map<EnvironmentAppearance, Collection<Event<?>>> result = new HashMap<>();
		// TODO if a message is received while this is happening? will it fail?
		// i dont know!
		recievedMessages.forEach((EnvironmentAppearance app,
				Collection<Event<?>> messages) -> {
			if (!messages.isEmpty()) {
				result.put(app, messages);
				recievedMessages.put(app, new ArrayList<>());
			}
		});
		return result;
	}

	/**
	 * This method should only be called by the {@link INetServer} which this
	 * {@link EnvironmentConnectionManager} is observing. This method will be
	 * called when a new connection has been made and will supply the new
	 * {@link INetSlave} that has been created by the {@link INetServer}.
	 */
	@Override
	public void update(Observable obs, Object arg) {
		if (INetServer.class.isAssignableFrom(obs.getClass())) {
			if (INetSlave.class.isAssignableFrom(arg.getClass())) {
				System.out.println("ADDING NEW CONNECTION!");
				new INetEnvironmentConnection(
						this.localenvironment.getAppearance(), (INetSlave) arg)
						.addReciever(this);
			}
		}
	}

	public void connectToEnvironment(String host, Integer port,
			AmbientRelation relation) {
		System.out.println("SLOW CONNECT");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		INetEnvironmentConnection connection = new INetEnvironmentConnection(
				this.localenvironment.getAppearance(), relation, server, host,
				port);
		connection.addReciever(this);
		addRemoteEnvironment(connection);
	}

	public void addRemoteEnvironment(INetEnvironmentConnection connection) {
		AmbientRelation remoteRelation = connection.getRelationship()
				.getSecond();
		System.out.println(this.localenvironment + " CONNECTED TO: "
				+ connection);
		// set the synchroniser
		connection.setSynchroniser(this.localenvironment.getPhysics()
				.getSynchroniser().addRemoteSynchroniser(connection));
		// TODO optimise, handle matching sub/neighbours
		if (remoteRelation.equals(AmbientRelation.SUB)) {
			this.subEnvironmentConnections.put(
					(EnvironmentAppearance) connection.getRemoteAppearance(),
					connection);
		} else if (remoteRelation.equals(AmbientRelation.NEIGHBOUR)) {
			this.neighbouringEnvironmentConnections.put(
					(EnvironmentAppearance) connection.getRemoteAppearance(),
					connection);
		} else if (remoteRelation.equals(AmbientRelation.SUPER)) {
			if (this.superEnvironmentConnection == null) {
				this.superEnvironmentConnection = connection;
			} else {
				String c = Environment.class.getSimpleName();
				System.err.println("Inconsistant " + c + " heirarchy, an " + c
						+ " cannot have multiple super " + c + "s: "
						+ this.superEnvironmentConnection + "," + connection);
				// TODO throw a custom exception?
			}
		}
	}

	@Override
	public synchronized void receive(Recipient recipient, Event<?> message) {
		if (InitialisationMessage.class.isAssignableFrom(message.getClass())) {
			if (INetEnvironmentConnection.class.isAssignableFrom(recipient
					.getClass())) {
				INetEnvironmentConnection connection = (INetEnvironmentConnection) recipient;
				addRemoteEnvironment(connection);
				connection.send(this.localenvironment
						.getDefaultActionSubscriptionMessage());
				this.numRemoteConnections++;
				System.out.println("NUM REMOTE CONNECTIONS: "
						+ numRemoteConnections);

			}
		} else {
			this.localenvironment
					.handleMessage(
							(EnvironmentAppearance) ((AbstractEnvironmentConnection) recipient)
									.getRemoteAppearance(), message);
		}
	}

	public void addSubEnviroment(AbstractEnvironment environment) {
		DefaultEnvironmentConnection connection = new DefaultEnvironmentConnection(
				localenvironment.getAppearance());
		((AbstractConnectedEnvironment) environment)
				.getConnectedEnvironmentManager()
				.setSuperEnvironmentConnection(connection);
		if (connection.getMutualConnector() != null) {
			this.addSubEnvironmentConnection(connection);
			recievedMessages.put(
					(EnvironmentAppearance) connection.getRemoteAppearance(),
					new ArrayList<>());
			connection.addReciever(EnvironmentConnectionManager.this);
		} else {
			System.out.println(environment + " refused connection");
		}
	}

	public void addNeighbouringEnvironmentConnection(
			AbstractEnvironmentConnection connection) {
		this.neighbouringEnvironmentConnections.put(
				(EnvironmentAppearance) connection.getRemoteAppearance(),
				connection);
	}

	public void addSubEnvironmentConnection(
			AbstractEnvironmentConnection connection) {
		this.subEnvironmentConnections.put(
				(EnvironmentAppearance) connection.getRemoteAppearance(),
				connection);
	}

	/**
	 * A super environment should call this method with its own
	 * {@link AbstractEnvironmentConnection}. The sub environment will then (if
	 * it accepts the super {@link Environment}) return its own
	 * {@link AbstractEnvironmentConnection}.
	 * 
	 * @param environment
	 */
	public void setSuperEnvironmentConnection(
			AbstractEnvironmentConnection connection) {
		this.superEnvironmentConnection = new DefaultEnvironmentConnection(
				(DefaultEnvironmentConnection) connection,
				localenvironment.getAppearance());
		recievedMessages.put(connection.getAppearance(), new ArrayList<>());
		this.superEnvironmentConnection
				.addReciever(EnvironmentConnectionManager.this);
	}

	public Collection<EnvironmentAppearance> getNeighbouringEnvironmentAppearances() {
		return this.neighbouringEnvironmentConnections.keySet();
	}

	public Collection<AbstractEnvironmentConnection> getNeighbouringEnvironmentConnections() {
		return this.neighbouringEnvironmentConnections.values();
	}

	public Collection<EnvironmentAppearance> getSubEnvironmentAppearances() {
		return this.subEnvironmentConnections.keySet();
	}

	public Collection<AbstractEnvironmentConnection> getSubEnvironmentConnections() {
		return this.subEnvironmentConnections.values();
	}

	public EnvironmentAppearance getSuperEnvironmentAppearance() {
		return this.superEnvironmentConnection.getAppearance();
	}

	public AbstractEnvironmentConnection getSuperEnvironmentConnection() {
		return this.superEnvironmentConnection;
	}

	public void sendToAllNeighbouringEnvironments(Event<?> obj) {
		neighbouringEnvironmentConnections.values().forEach(
				(AbstractEnvironmentConnection c) -> {
					c.send(obj);
				});
	}

	public void sendToAllSubEnvironments(Event<?> obj) {
		subEnvironmentConnections.values().forEach(
				(AbstractEnvironmentConnection c) -> {
					c.send(obj);
				});
	}

	public void sendToEnvironment(EnvironmentAppearance environment,
			Event<?> obj) {
		AbstractEnvironmentConnection con;
		if ((con = subEnvironmentConnections.get(environment)) != null) {
			con.send(obj);
		} else if ((con = neighbouringEnvironmentConnections.get(environment)) != null) {
			con.send(obj);
		} else if (environment.equals(superEnvironmentConnection
				.getRemoteAppearance())) {
			superEnvironmentConnection.send(obj);
		} else {
			System.err.println("Cannot send: " + obj
					+ " to unknown environment: " + environment);
		}
	}

	public void sendToEnvironments(
			Collection<EnvironmentAppearance> environments, Event<?> obj) {
		if (environments != null) {
			System.out.println(localenvironment + " SEND: " + obj + " TO: "
					+ environments);
			if (subEnvironmentConnections != null) {
				Collection<AbstractEnvironmentConnection> envs = getAll(
						this.subEnvironmentConnections, environments);
				envs.forEach((AbstractEnvironmentConnection c) -> c.send(obj));
			}
			if (neighbouringEnvironmentConnections != null) {
				Collection<AbstractEnvironmentConnection> envs = getAll(
						this.neighbouringEnvironmentConnections, environments);
				envs.forEach((AbstractEnvironmentConnection c) -> c.send(obj));
			}
			if (superEnvironmentConnection != null) {
				if (environments.contains(superEnvironmentConnection
						.getRemoteAppearance())) {
					superEnvironmentConnection.send(obj);
				}
			}
		}
	}

	public void sendToNeighbouringEnvironment(EnvironmentAppearance appearance,
			Event<?> obj) {
		neighbouringEnvironmentConnections.get(appearance).send(obj);
	}

	public void sendToSubEnvironment(EnvironmentAppearance appearance,
			Event<?> obj) {
		subEnvironmentConnections.get(appearance).send(obj);
	}

	public void sendToSuperEnvironment(Event<?> obj) {
		superEnvironmentConnection.send(obj);
	}

	// utility function TODO move to utils
	protected <K, V> Collection<V> getAll(Map<K, V> map, Collection<K> keys) {
		Map<K, V> newMap = new HashMap<K, V>(map);
		newMap.keySet().retainAll(keys);
		return newMap.values();
	}

	public boolean isDistributed() {
		return server != null;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(this.getClass()
				.getSimpleName() + System.lineSeparator());
		builder.append("  LOCAL: " + this.localenvironment
				+ System.lineSeparator());
		builder.append("  SUPER: " + this.superEnvironmentConnection
				+ System.lineSeparator() + "  SUB: " + System.lineSeparator());
		this.subEnvironmentConnections.values().forEach(
				(con) -> builder.append("    " + con + System.lineSeparator()));
		builder.append("  NEIGHBOUR: " + System.lineSeparator());
		this.neighbouringEnvironmentConnections.values().forEach(
				(con) -> builder.append("    " + con + System.lineSeparator()));
		return builder.toString();
	}
}
