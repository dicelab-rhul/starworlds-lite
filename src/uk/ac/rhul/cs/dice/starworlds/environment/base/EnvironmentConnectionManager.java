package uk.ac.rhul.cs.dice.starworlds.environment.base;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import uk.ac.rhul.cs.dice.starworlds.appearances.EnvironmentAppearance;
import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.Environment;
import uk.ac.rhul.cs.dice.starworlds.environment.concrete.DefaultEnvironmentConnection;
import uk.ac.rhul.cs.dice.starworlds.environment.inet.INetDefaultServer;
import uk.ac.rhul.cs.dice.starworlds.utils.inet.INetServer;

public final class EnvironmentConnectionManager implements Receiver {

	private static final String[] STATEERROR = new String[] {
			"Error: Cannot set environment: ",
			System.lineSeparator() + "Invalid state: ",
			System.lineSeparator()
					+ "See ConnectedEnvironmentManager documentation for details." };

	public static final EnvironmentConnectionType LOCAL = EnvironmentConnectionType.LOCAL;
	public static final EnvironmentConnectionType REMOTE = EnvironmentConnectionType.REMOTE;
	public static final EnvironmentConnectionType MIXED = EnvironmentConnectionType.MIXED;

	private ConnectedEnvironmentState state;
	private AbstractConnectedEnvironment localenvironment;
	protected Map<EnvironmentAppearance, Collection<AbstractMessage<?>>> recievedMessages;

	/**
	 * Constructor specifically for {@link EnvironmentConnectionType#MIXED
	 * MIXED} connections.
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
			AbstractConnectedEnvironment localenvironment,
			AbstractConnectedEnvironment localsuperenvironment,
			Collection<AbstractConnectedEnvironment> localsubenvironments,
			Collection<AbstractConnectedEnvironment> localneighbouringenvironments,
			Integer port) {
		this.recievedMessages = new HashMap<>();
		this.state = MIXED.getState(this);
		this.localenvironment = localenvironment;
		initialiseLocalEnvironments(localsubenvironments,
				localneighbouringenvironments);
		this.state.setPort(port);
	}

	/**
	 * Constructor specifically for {@link EnvironmentConnectionType#LOCAL
	 * LOCAL} connections.
	 * 
	 * @param localenvironment
	 *            : the {@link Environment} that this
	 *            {@link EnvironmentConnectionManager} is managing
	 * @param localsubenvironments
	 *            : the local {@link Environment}s sub {@link Environment}s
	 * @param localneighbouringenvironments
	 *            : the local {@link Environment}s neighbouring
	 *            {@link Environment}s
	 */
	public EnvironmentConnectionManager(
			AbstractConnectedEnvironment localenvironment,
			Collection<AbstractConnectedEnvironment> localsubenvironments,
			Collection<AbstractConnectedEnvironment> localneighbouringenvironments) {
		this.recievedMessages = new HashMap<>();
		this.state = LOCAL.getState(this);
		this.localenvironment = localenvironment;
		initialiseLocalEnvironments(localsubenvironments,
				localneighbouringenvironments);
	}

	/**
	 * Constructor specifically for {@link EnvironmentConnectionType#REMOTE
	 * REMOTE} connections.
	 * 
	 * @param localenvironment
	 *            : the {@link Environment} that this
	 * @param port
	 *            : the port that any remote {@link Environment} will try to
	 *            connect to
	 */
	public EnvironmentConnectionManager(
			AbstractConnectedEnvironment localenvironment, Integer port) {
		this.recievedMessages = new HashMap<>();
		this.state = REMOTE.getState(this);
		this.state.setPort(port);
		this.localenvironment = localenvironment;

	}

	/**
	 * Constructor. This constructor should not generally be used. Custom
	 * {@link Environment} setup is required to avoid errors. Namely
	 * {@link AbstractEnvironment} should be sub classed.
	 * 
	 * @param state
	 *            : {@link EnvironmentConnectionType#LOCAL LOCAL},
	 *            {@link EnvironmentConnectionType#REMOTE REMOTE} or
	 *            {@link EnvironmentConnectionType#MIXED MIXED} see
	 *            {@link EnvironmentConnectionManager}.
	 * @param port
	 *            of the local {@link Environment}, may be null if
	 *            {@link EnvironmentConnectionType#LOCAL LOCAL}
	 */
	public EnvironmentConnectionManager(EnvironmentConnectionType state,
			Integer port) {
		this.recievedMessages = new HashMap<>();
		this.state = state.getState(this);
		this.state.setPort(port);
	}

	/**
	 * unused?
	 */
	public Map<EnvironmentAppearance, Collection<AbstractMessage<?>>> flushMessages() {
		Map<EnvironmentAppearance, Collection<AbstractMessage<?>>> result = new HashMap<>();
		// TODO if a message is received while this is happening? will it fail?
		// i dont know!
		recievedMessages.forEach((EnvironmentAppearance app,
				Collection<AbstractMessage<?>> messages) -> {
			if (!messages.isEmpty()) {
				result.put(app, messages);
				recievedMessages.put(app, new ArrayList<>());
			}
		});
		return result;
	}

	@Override
	public synchronized void receive(Recipient recipient,
			AbstractMessage<?> message) {
//		System.out.println("Received: " + System.lineSeparator() + "    "
//				+ recipient + System.lineSeparator() + "        " + message);
		this.localenvironment.handleMessage(
				((AbstractEnvironmentConnection) recipient)
						.getRemoteAppearance(), message);
		// perhaps this? depends on what messages are received
		// this.recievedMessages.get(
		// ((AbstractEnvironmentConnection) recipient)
		// .getRemoteAppearance()).add(message);
	}

	public void addNeighbouringEnvironment(String host, int port) {
		// TODO
		this.state.addNeighbouringEnvironment(host, port);
	}

	/**
	 * A super environment should call this method with its own
	 * {@link AbstractEnvironmentConnection}. The sub environment will then (if
	 * it accepts the super {@link Environment}) return its own
	 * {@link AbstractEnvironmentConnection}.
	 * 
	 * @param environment
	 */
	public void setSuperEnvironment(AbstractEnvironmentConnection connection) {
		this.state.setSuperEnvironmentConnection(connection);
	}

	public void setSuperEnvironment(String host, int port) {
		this.state.setSuperEnvironment(host, port);
	}

	public void addSubEnviroment(AbstractEnvironment environment) {
		System.out.println("ADDING SUB ENVIRONMENT: "
				+ environment.getAppearance());
		DefaultEnvironmentConnection connection = new DefaultEnvironmentConnection(
				localenvironment.getAppearance());
		((AbstractConnectedEnvironment) environment)
				.getConnectedEnvironmentManager().setSuperEnvironment(
						connection);
		if (connection.getMutualConnector() != null) {
			this.state.addSubEnvironmentConnection(connection);
			recievedMessages.put(connection.getRemoteAppearance(),
					new ArrayList<>());
			connection.addReciever(EnvironmentConnectionManager.this);
		} else {
			System.out.println(environment + " refused connection");
		}
	}

	public void addSubEnvironment(String host, int port) {
		this.state.addSubEnvironment(host, port);
	}

	public Collection<EnvironmentAppearance> getNeighbouringEnvironmentAppearances() {
		return this.state.getNeighbouringEnvironmentAppearances();
	}

	public Collection<AbstractEnvironmentConnection> getNeighbouringEnvironments() {
		return this.state.getNeighbouringEnvironmentConnections();
	}

	public Collection<SocketAddress> getRemoteNeighbouringEnvironment() {
		return this.state.getRemoteNeighbouringEnvironments();
	}

	public Collection<EnvironmentAppearance> getRemoteNeighbouringEnvironmentAppearances() {
		return this.state.getRemoteNeighbouringEnvironmentAppearances();
	}

	public Collection<EnvironmentAppearance> getRemoteSubEnvironmentAppearances() {
		return this.state.getRemoteSubEnvironmentAppearances();
	}

	public Collection<SocketAddress> getRemoteSubEnvironments() {
		return this.state.getRemoteSubEnvironments();
	}

	public SocketAddress getRemoteSuperEnvironment() {
		return this.state.getRemoteSuperEnvironment();
	}

	public ConnectedEnvironmentState getState() {
		return state;
	}

	public Collection<EnvironmentAppearance> getSubEnvironmentAppearances() {
		return this.state.getSubEnvironmentAppearances();
	}

	public Collection<AbstractEnvironmentConnection> getSubEnvironments() {
		return this.state.getSubEnvironmentConnections();
	}

	public AbstractEnvironmentConnection getSuperEnvironment() {
		return this.state.getSuperEnvironmentConnection();
	}

	public EnvironmentAppearance getSuperEnvironmentAppearance() {
		return this.state.getSuperEnvironmentAppearance();
	}

	private void initialiseLocalEnvironments(
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

	private void invalidstate(ConnectedEnvironmentState state,
			AbstractEnvironmentConnection connection) {
		System.err.println(STATEERROR[0] + connection + STATEERROR[1] + state
				+ STATEERROR[2]);
	}

	private void invalidstate(ConnectedEnvironmentState state, String host,
			int port) {
		System.err.println(STATEERROR[0] + "[" + host + ":" + port + "]"
				+ STATEERROR[1] + state + STATEERROR[2]);
	}

	//
	// public void addNeighbouringEnvironment(AbstractEnvironment environment) {
	// this.state.addNeighbouringEnvironment(environment);
	// }

	public void sendToAllNeighbouringEnvironments(AbstractMessage<?> obj) {
		state.sendToAllNeighbouringEnvironments(obj);
	}

	public void sendToAllSubEnvironments(AbstractMessage<?> obj) {
		state.sendToAllSubEnvironments(obj);
	}

	public void sendToEnvironment(EnvironmentAppearance environment,
			AbstractMessage<?> obj) {
		state.sendToEnvironment(environment, obj);
	}

	public void sendToEnvironments(
			Collection<EnvironmentAppearance> environments,
			AbstractMessage<?> obj) {
//		System.out
//				.println("Sending to environments: " + System.lineSeparator()
//						+ "    " + environments + System.lineSeparator()
//						+ "    " + obj);
		state.sendToEnvironments(environments, obj);
	}

	public void sendToNeighbouringEnvironment(EnvironmentAppearance appearance,
			AbstractMessage<?> obj) {
		this.state.sendToNeighbouringEnvironment(appearance, obj);
	}

	public void sendToSubEnvironment(EnvironmentAppearance appearance,
			AbstractMessage<?> obj) {
		this.state.sendToSubEnvironment(appearance, obj);
	}

	public void sendToSuperEnvironment(AbstractMessage<?> obj) {
		this.state.sendToSuperEnvironment(obj);
	}

	// utility function TODO move to utils
	protected <K, V> Collection<V> getAll(Map<K, V> map, Collection<K> keys) {
		Map<K, V> newMap = new HashMap<K, V>(map);
		newMap.keySet().retainAll(keys);
		return newMap.values();
	}

	private interface ConnectedEnvironmentState {

		public void addNeighbouringEnvironment(String host, int port);

		public void addNeighbouringEnvironmentConnection(
				AbstractEnvironmentConnection environment);

		public void addSubEnvironment(String host, int port);

		public void addSubEnvironmentConnection(
				AbstractEnvironmentConnection environment);

		public Collection<EnvironmentAppearance> getNeighbouringEnvironmentAppearances();

		public Collection<AbstractEnvironmentConnection> getNeighbouringEnvironmentConnections();

		public Collection<EnvironmentAppearance> getRemoteNeighbouringEnvironmentAppearances();

		public Collection<SocketAddress> getRemoteNeighbouringEnvironments();

		public Collection<EnvironmentAppearance> getRemoteSubEnvironmentAppearances();

		public Collection<SocketAddress> getRemoteSubEnvironments();

		public SocketAddress getRemoteSuperEnvironment();

		public Collection<EnvironmentAppearance> getSubEnvironmentAppearances();

		public Collection<AbstractEnvironmentConnection> getSubEnvironmentConnections();

		public EnvironmentAppearance getSuperEnvironmentAppearance();

		public AbstractEnvironmentConnection getSuperEnvironmentConnection();

		public void sendToAllNeighbouringEnvironments(AbstractMessage<?> obj);

		public void sendToAllSubEnvironments(AbstractMessage<?> obj);

		public void sendToEnvironment(EnvironmentAppearance environment,
				AbstractMessage<?> obj);

		public void sendToEnvironments(
				Collection<EnvironmentAppearance> environments,
				AbstractMessage<?> obj);

		public void sendToNeighbouringEnvironment(
				EnvironmentAppearance appearance, AbstractMessage<?> obj);

		public void sendToSubEnvironment(EnvironmentAppearance appearance,
				AbstractMessage<?> obj);

		public void sendToSuperEnvironment(AbstractMessage<?> obj);

		public void setPort(Integer port);

		public void setSuperEnvironment(String host, int port);

		public void setSuperEnvironmentConnection(
				AbstractEnvironmentConnection mutualConnector);

	}

	public enum EnvironmentConnectionType {

		REMOTE {
			public ConnectedEnvironmentState getState(
					EnvironmentConnectionManager manager) {
				return manager.new RemoteConnectedEnvironmentState();
			}
		},
		LOCAL {
			public ConnectedEnvironmentState getState(
					EnvironmentConnectionManager manager) {
				return manager.new LocalConnectedEnvironmentState();
			}
		},
		MIXED {
			public ConnectedEnvironmentState getState(
					EnvironmentConnectionManager manager) {
				return manager.new MixedConnectedEnvironmentState();
			}
		};

		public abstract ConnectedEnvironmentState getState(
				EnvironmentConnectionManager manager);
	}

	private class LocalConnectedEnvironmentState implements
			ConnectedEnvironmentState {

		private Map<EnvironmentAppearance, AbstractEnvironmentConnection> subEnvironmentConnections;
		private Map<EnvironmentAppearance, AbstractEnvironmentConnection> neighbouringEnvironmentsConnections;
		private AbstractEnvironmentConnection superEnvironmentConnection;

		public LocalConnectedEnvironmentState() {
			subEnvironmentConnections = new HashMap<>();
			neighbouringEnvironmentsConnections = new HashMap<>();
		}

		@Override
		public void addNeighbouringEnvironment(String host, int port) {
			invalidstate(this, host, port);
		}

		@Override
		public void addNeighbouringEnvironmentConnection(
				AbstractEnvironmentConnection mutualConnector) {
			// this.subEnvironments.put(environment.getAppearance(),
			// new DefaultEnvironmentConnection(
			// (AbstractConnectedEnvironment) environment);
		}

		@Override
		public void addSubEnvironment(String host, int port) {
			invalidstate(this, host, port);
		}

		@Override
		public void addSubEnvironmentConnection(
				AbstractEnvironmentConnection connection) {
			this.subEnvironmentConnections.put(
					connection.getRemoteAppearance(), connection);

		}

		@Override
		public void setSuperEnvironmentConnection(
				AbstractEnvironmentConnection connection) {
			this.superEnvironmentConnection = new DefaultEnvironmentConnection(
					(DefaultEnvironmentConnection) connection,
					localenvironment.getAppearance());
			recievedMessages.put(connection.getAppearance(), new ArrayList<>());
			this.superEnvironmentConnection
					.addReciever(EnvironmentConnectionManager.this);
		}

		@Override
		public Collection<EnvironmentAppearance> getNeighbouringEnvironmentAppearances() {
			return this.neighbouringEnvironmentsConnections.keySet();
		}

		@Override
		public Collection<AbstractEnvironmentConnection> getNeighbouringEnvironmentConnections() {
			return this.neighbouringEnvironmentsConnections.values();
		}

		@Override
		public Collection<EnvironmentAppearance> getRemoteNeighbouringEnvironmentAppearances() {
			return new HashSet<>(0);
		}

		@Override
		public Collection<SocketAddress> getRemoteNeighbouringEnvironments() {
			return new HashSet<>(0);
		}

		@Override
		public Collection<EnvironmentAppearance> getRemoteSubEnvironmentAppearances() {
			return new HashSet<>(0);
		}

		@Override
		public Collection<SocketAddress> getRemoteSubEnvironments() {
			return new HashSet<>(0);
		}

		@Override
		public SocketAddress getRemoteSuperEnvironment() {
			return null;
		}

		@Override
		public Collection<EnvironmentAppearance> getSubEnvironmentAppearances() {
			return this.subEnvironmentConnections.keySet();
		}

		@Override
		public Collection<AbstractEnvironmentConnection> getSubEnvironmentConnections() {
			return this.subEnvironmentConnections.values();
		}

		@Override
		public EnvironmentAppearance getSuperEnvironmentAppearance() {
			return this.superEnvironmentConnection.getAppearance();
		}

		@Override
		public AbstractEnvironmentConnection getSuperEnvironmentConnection() {
			return this.superEnvironmentConnection;
		}

		@Override
		public void sendToAllNeighbouringEnvironments(AbstractMessage<?> obj) {
			neighbouringEnvironmentsConnections.values().forEach(
					(AbstractEnvironmentConnection c) -> {
						c.send(obj);
					});
		}

		@Override
		public void sendToAllSubEnvironments(AbstractMessage<?> obj) {
			subEnvironmentConnections.values().forEach(
					(AbstractEnvironmentConnection c) -> {
						c.send(obj);
					});
		}

		@Override
		public void sendToEnvironment(EnvironmentAppearance environment,
				AbstractMessage<?> obj) {
			AbstractEnvironmentConnection con;
			if ((con = subEnvironmentConnections.get(environment)) != null) {
				con.send(obj);
			} else if ((con = neighbouringEnvironmentsConnections
					.get(environment)) != null) {
				con.send(obj);
			} else if (environment.equals(superEnvironmentConnection.getRemoteAppearance())) {
				superEnvironmentConnection.send(obj);
			} else {
				System.err.println("Cannot send: " + obj
						+ " to unknown environment: " + environment);
			}
		}

		@Override
		public void sendToEnvironments(
				Collection<EnvironmentAppearance> environments,
				AbstractMessage<?> obj) {
			if (environments != null) {
				// System.out.println("SEND: " + obj);
				if (subEnvironmentConnections != null) {
					Collection<AbstractEnvironmentConnection> envs = getAll(
							this.subEnvironmentConnections, environments);
					// System.out.println("THIS: " + localenvironment.getId());
					// System.out.println("CHECK: " +
					// this.subEnvironmentConnections);
					// System.out.println("MENT FOR: " + environments);
					// System.out.println("TO: " + envs);
					envs.forEach((AbstractEnvironmentConnection c) -> c
							.send(obj));
				}
				if (superEnvironmentConnection != null) {
					// System.out.println("MENT FOR: " + environments);
					// System.out.println("CHECK: " +
					// this.superEnvironmentConnection);
					if (environments.contains(superEnvironmentConnection
							.getRemoteAppearance())) {
						superEnvironmentConnection.send(obj);
					}
				}
			}
		}

		@Override
		public void sendToNeighbouringEnvironment(
				EnvironmentAppearance appearance, AbstractMessage<?> obj) {
			neighbouringEnvironmentsConnections.get(appearance).send(obj);
		}

		@Override
		public void sendToSubEnvironment(EnvironmentAppearance appearance,
				AbstractMessage<?> obj) {
			subEnvironmentConnections.get(appearance).send(obj);
		}

		@Override
		public void sendToSuperEnvironment(AbstractMessage<?> obj) {
			superEnvironmentConnection.send(obj);
		}

		@Override
		public void setPort(Integer port) {
			// unused by local environments
		}

		@Override
		public void setSuperEnvironment(String host, int port) {
			invalidstate(this, host, port);
		}

	}

	private class MixedConnectedEnvironmentState implements
			ConnectedEnvironmentState {

		private boolean superIsRemote;
		private RemoteConnectedEnvironmentState remotestate;
		private LocalConnectedEnvironmentState localstate;

		public MixedConnectedEnvironmentState() {
			remotestate = new RemoteConnectedEnvironmentState();
			localstate = new LocalConnectedEnvironmentState();
		}

		@Override
		public void addNeighbouringEnvironment(String host, int port) {
			this.remotestate.addNeighbouringEnvironment(host, port);
		}

		@Override
		public void addNeighbouringEnvironmentConnection(
				AbstractEnvironmentConnection environment) {
			this.localstate.addNeighbouringEnvironmentConnection(environment);
		}

		@Override
		public void addSubEnvironment(String host, int port) {
			this.remotestate.addSubEnvironment(host, port);
		}

		@Override
		public void addSubEnvironmentConnection(
				AbstractEnvironmentConnection environment) {
			this.localstate.addSubEnvironmentConnection(environment);
		}

		@Override
		public Collection<EnvironmentAppearance> getNeighbouringEnvironmentAppearances() {
			return this.localstate.getNeighbouringEnvironmentAppearances();
		}

		@Override
		public Collection<AbstractEnvironmentConnection> getNeighbouringEnvironmentConnections() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Collection<EnvironmentAppearance> getRemoteNeighbouringEnvironmentAppearances() {
			return this.remotestate
					.getRemoteNeighbouringEnvironmentAppearances();
		}

		@Override
		public Collection<SocketAddress> getRemoteNeighbouringEnvironments() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Collection<EnvironmentAppearance> getRemoteSubEnvironmentAppearances() {
			return this.remotestate.getRemoteSubEnvironmentAppearances();
		}

		@Override
		public Collection<SocketAddress> getRemoteSubEnvironments() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public SocketAddress getRemoteSuperEnvironment() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Collection<EnvironmentAppearance> getSubEnvironmentAppearances() {
			return this.localstate.getSubEnvironmentAppearances();
		}

		@Override
		public Collection<AbstractEnvironmentConnection> getSubEnvironmentConnections() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public EnvironmentAppearance getSuperEnvironmentAppearance() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public AbstractEnvironmentConnection getSuperEnvironmentConnection() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void sendToAllNeighbouringEnvironments(AbstractMessage<?> obj) {
			this.remotestate.sendToAllNeighbouringEnvironments(obj);
			this.localstate.sendToAllNeighbouringEnvironments(obj);
		}

		@Override
		public void sendToAllSubEnvironments(AbstractMessage<?> obj) {
			this.remotestate.sendToAllSubEnvironments(obj);
			this.localstate.sendToAllSubEnvironments(obj);
		}

		@Override
		public void sendToEnvironment(EnvironmentAppearance environment,
				AbstractMessage<?> obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void sendToEnvironments(
				Collection<EnvironmentAppearance> environments,
				AbstractMessage<?> obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void sendToNeighbouringEnvironment(
				EnvironmentAppearance appearance, AbstractMessage<?> obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void sendToSubEnvironment(EnvironmentAppearance appearance,
				AbstractMessage<?> obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void sendToSuperEnvironment(AbstractMessage<?> obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setPort(Integer port) {
			this.remotestate.setPort(port);
		}

		@Override
		public void setSuperEnvironment(String host, int port) {
			this.superIsRemote = true;
			this.remotestate.setSuperEnvironment(host, port);
			this.localstate.superEnvironmentConnection = null;
		}

		@Override
		public void setSuperEnvironmentConnection(
				AbstractEnvironmentConnection environment) {
			this.superIsRemote = false;
			this.remotestate.superEnvironment = null;
			this.localstate.setSuperEnvironmentConnection(environment);
		}
	}

	private class RemoteConnectedEnvironmentState implements
			ConnectedEnvironmentState {

		private INetServer server;

		private Map<EnvironmentAppearance, SocketAddress> subEnvironments;
		private Map<EnvironmentAppearance, AbstractEnvironment> neighbouringEnvironments;
		private AbstractEnvironment superEnvironment;

		public RemoteConnectedEnvironmentState() {
			subEnvironments = new HashMap<>();
			neighbouringEnvironments = new HashMap<>();
		}

		@Override
		public void addNeighbouringEnvironment(String host, int port) {
			// TODO Auto-generated method stub

		}

		@Override
		public void addNeighbouringEnvironmentConnection(
				AbstractEnvironmentConnection mutualConnector) {
			invalidstate(this, mutualConnector);
		}

		@Override
		public void addSubEnvironment(String host, int port) {
			// TODO Auto-generated method stub

		}

		@Override
		public void addSubEnvironmentConnection(
				AbstractEnvironmentConnection mutualConnector) {
			invalidstate(this, mutualConnector);
		}

		@Override
		public Collection<EnvironmentAppearance> getNeighbouringEnvironmentAppearances() {
			return new HashSet<>(0);
		}

		@Override
		public Collection<AbstractEnvironmentConnection> getNeighbouringEnvironmentConnections() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Collection<EnvironmentAppearance> getRemoteNeighbouringEnvironmentAppearances() {
			return this.neighbouringEnvironments.keySet();
		}

		@Override
		public Collection<SocketAddress> getRemoteNeighbouringEnvironments() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Collection<EnvironmentAppearance> getRemoteSubEnvironmentAppearances() {
			return this.subEnvironments.keySet();
		}

		@Override
		public Collection<SocketAddress> getRemoteSubEnvironments() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public SocketAddress getRemoteSuperEnvironment() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Collection<EnvironmentAppearance> getSubEnvironmentAppearances() {
			return new HashSet<>(0);
		}

		@Override
		public Collection<AbstractEnvironmentConnection> getSubEnvironmentConnections() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public EnvironmentAppearance getSuperEnvironmentAppearance() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public AbstractEnvironmentConnection getSuperEnvironmentConnection() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void sendToAllNeighbouringEnvironments(AbstractMessage<?> obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void sendToAllSubEnvironments(AbstractMessage<?> obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void sendToEnvironment(EnvironmentAppearance environment,
				AbstractMessage<?> obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void sendToEnvironments(
				Collection<EnvironmentAppearance> environments,
				AbstractMessage<?> obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void sendToNeighbouringEnvironment(
				EnvironmentAppearance appearance, AbstractMessage<?> obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void sendToSubEnvironment(EnvironmentAppearance appearance,
				AbstractMessage<?> obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void sendToSuperEnvironment(AbstractMessage<?> obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setPort(Integer port) {
			/*
			 * TODO should this be able to be changed, i.e. to use a different
			 * server class?
			 */
			this.server = new INetDefaultServer(port);
		}

		@Override
		public void setSuperEnvironment(String host, int port) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setSuperEnvironmentConnection(
				AbstractEnvironmentConnection mutualConnector) {
			invalidstate(this, mutualConnector);
		}

	}
}
