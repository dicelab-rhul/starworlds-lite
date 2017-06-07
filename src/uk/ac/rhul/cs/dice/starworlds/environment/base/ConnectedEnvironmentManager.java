package uk.ac.rhul.cs.dice.starworlds.environment.base;

import java.net.SocketAddress;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import uk.ac.rhul.cs.dice.starworlds.appearances.EnvironmentAppearance;
import uk.ac.rhul.cs.dice.starworlds.environment.inet.INetDefaultServer;
import uk.ac.rhul.cs.dice.starworlds.utils.inet.INetServer;

public final class ConnectedEnvironmentManager {

	private static final String[] STATEERROR = new String[] {
			"Error: Cannot set environment: ",
			System.lineSeparator() + "Invalid state: ",
			System.lineSeparator()
					+ "See ConnectedEnvironmentManager documentation for details." };

	public static final EnvironmentConnection LOCAL = EnvironmentConnection.LOCAL;
	public static final EnvironmentConnection REMOTE = EnvironmentConnection.REMOTE;
	public static final EnvironmentConnection MIXED = EnvironmentConnection.MIXED;

	private ConnectedEnvironmentState state;

	public ConnectedEnvironmentManager(EnvironmentConnection state, Integer port) {
		this.state = state.getState(this);
	}

	public ConnectedEnvironmentState getState() {
		return state;
	}

	// // **** GET APPEARNACE METHODS **** ////

	public EnvironmentAppearance getSuperEnvironmentAppearance() {
		return this.state.getSuperEnvironmentAppearance();
	}

	public Collection<EnvironmentAppearance> getSubEnvironmentAppearances() {
		return this.state.getSubEnvironmentAppearances();
	}

	public Collection<EnvironmentAppearance> getRemoteSubEnvironmentAppearances() {
		return this.state.getRemoteSubEnvironmentAppearances();
	}

	public Collection<EnvironmentAppearance> getNeighbouringEnvironmentAppearances() {
		return this.state.getNeighbouringEnvironmentAppearances();
	}

	public Collection<EnvironmentAppearance> getRemoteNeighbouringEnvironmentAppearances() {
		return this.state.getRemoteNeighbouringEnvironmentAppearances();
	}

	// // **** GET ENVIRONMENT METHODS **** ////

	public AbstractEnvironment getSuperEnvironment() {
		return this.state.getSuperEnvironment();
	}

	public Collection<AbstractEnvironment> getSubEnvironments() {
		return this.state.getSubEnvironments();
	}

	public Collection<AbstractEnvironment> getNeighbouringEnvironments() {
		return this.state.getNeighbouringEnvironments();
	}

	// // **** GET ENVIRONMENT METHODS **** ////

	public SocketAddress getRemoteSuperEnvironment() {
		return this.state.getRemoteSuperEnvironment();
	}

	public Collection<SocketAddress> getRemoteSubEnvironments() {
		return this.state.getRemoteSubEnvironments();
	}

	public Collection<SocketAddress> getRemoteNeighbouringEnvironment() {
		return this.state.getRemoteNeighbouringEnvironments();
	}

	public void addSuperEnvironment(String host, int port) {
		this.state.setSuperEnvironment(host, port);
	}

	public void addSubEnvironment(String host, int port) {
		this.state.addSubEnvironment(host, port);
	}

	public void addNeighbouringEnvironment(String host, int port) {
		this.state.addNeighbouringEnvironment(host, port);
	}

	public void addSuperEnvironment(AbstractEnvironment environment) {
		this.state.setSuperEnvironment(environment);
	}

	public void addSubEnvironment(AbstractEnvironment environment) {
		this.state.addSubEnvironment(environment);
	}

	public void addNeighbouringEnvironment(AbstractEnvironment environment) {
		this.state.addNeighbouringEnvironment(environment);
	}

	public void sendToSuperEnvironment(Object obj) {
		this.state.sendToSuperEnvironment(obj);
	}

	public void sendToSubEnvironment(EnvironmentAppearance appearance,
			Object obj) {
		this.state.sendToSubEnvironment(appearance, obj);
	}

	public void sendToNeighbouringEnvironment(EnvironmentAppearance appearance,
			Object obj) {
		this.state.sendToNeighbouringEnvironment(appearance, obj);
	}

	public void sendToAllNeighbouringEnvironments(Object obj) {
		state.sendToAllNeighbouringEnvironments(obj);
	}

	public void sendToAllSubEnvironments(Object obj) {
		state.sendToAllSubEnvironments(obj);
	}

	private void invalidstate(ConnectedEnvironmentState state,
			AbstractEnvironment environment) {
		System.err.println(STATEERROR[0] + environment + STATEERROR[1] + state
				+ STATEERROR[2]);
	}

	private void invalidstate(ConnectedEnvironmentState state, String host,
			int port) {
		System.err.println(STATEERROR[0] + "[" + host + ":" + port + "]"
				+ STATEERROR[1] + state + STATEERROR[2]);
	}

	public enum EnvironmentConnection {

		REMOTE {
			public ConnectedEnvironmentState getState(
					ConnectedEnvironmentManager manager) {
				return manager.new RemoteConnectedEnvironmentState();
			}
		},
		LOCAL {
			public ConnectedEnvironmentState getState(
					ConnectedEnvironmentManager manager) {
				return manager.new LocalConnectedEnvironmentState();
			}
		},
		MIXED {
			public ConnectedEnvironmentState getState(
					ConnectedEnvironmentManager manager) {
				return manager.new MixedConnectedEnvironmentState();
			}
		};

		public abstract ConnectedEnvironmentState getState(
				ConnectedEnvironmentManager manager);
	}

	private class RemoteConnectedEnvironmentState implements
			ConnectedEnvironmentState {

		private INetServer server;

		private Map<EnvironmentAppearance, SocketAddress> subEnvironments = new HashMap<>();
		private Map<EnvironmentAppearance, AbstractEnvironment> neighbouringEnvironments = new HashMap<>();
		private AbstractEnvironment superEnvironment;

		@Override
		public void sendToAllNeighbouringEnvironments(Object obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void sendToAllSubEnvironments(Object obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void sendToSuperEnvironment(Object obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void sendToSubEnvironment(EnvironmentAppearance appearance,
				Object obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void sendToNeighbouringEnvironment(
				EnvironmentAppearance appearance, Object obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setSuperEnvironment(String host, int port) {
			// TODO Auto-generated method stub

		}

		@Override
		public void addSubEnvironment(String host, int port) {
			// TODO Auto-generated method stub

		}

		@Override
		public void addNeighbouringEnvironment(String host, int port) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setSuperEnvironment(AbstractEnvironment environment) {
			invalidstate(this, environment);
		}

		@Override
		public void addSubEnvironment(AbstractEnvironment environment) {
			invalidstate(this, environment);
		}

		@Override
		public void addNeighbouringEnvironment(AbstractEnvironment environment) {
			invalidstate(this, environment);
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
		public Collection<EnvironmentAppearance> getSubEnvironmentAppearances() {
			return new HashSet<>(0);
		}

		@Override
		public Collection<EnvironmentAppearance> getNeighbouringEnvironmentAppearances() {
			return new HashSet<>(0);
		}

		@Override
		public Collection<EnvironmentAppearance> getRemoteSubEnvironmentAppearances() {
			return this.subEnvironments.keySet();
		}

		@Override
		public Collection<EnvironmentAppearance> getRemoteNeighbouringEnvironmentAppearances() {
			return this.neighbouringEnvironments.keySet();
		}

		@Override
		public EnvironmentAppearance getSuperEnvironmentAppearance() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Collection<AbstractEnvironment> getSubEnvironments() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Collection<AbstractEnvironment> getNeighbouringEnvironments() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public SocketAddress getRemoteSuperEnvironment() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Collection<SocketAddress> getRemoteSubEnvironments() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Collection<SocketAddress> getRemoteNeighbouringEnvironments() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public AbstractEnvironment getSuperEnvironment() {
			// TODO Auto-generated method stub
			return null;
		}
	}

	private class LocalConnectedEnvironmentState implements
			ConnectedEnvironmentState {

		private Map<EnvironmentAppearance, AbstractEnvironment> subEnvironments = new HashMap<>();
		private Map<EnvironmentAppearance, AbstractEnvironment> neighbouringEnvironments = new HashMap<>();
		private AbstractEnvironment superEnvironment;

		@Override
		public void sendToAllNeighbouringEnvironments(Object obj) {

		}

		@Override
		public void sendToAllSubEnvironments(Object obj) {

		}

		@Override
		public void sendToSuperEnvironment(Object obj) {
		}

		@Override
		public void sendToSubEnvironment(EnvironmentAppearance appearance,
				Object obj) {
		}

		@Override
		public void sendToNeighbouringEnvironment(
				EnvironmentAppearance appearance, Object obj) {
		}

		@Override
		public void setSuperEnvironment(String host, int port) {
			invalidstate(this, host, port);
		}

		@Override
		public void addSubEnvironment(String host, int port) {
			invalidstate(this, host, port);
		}

		@Override
		public void addNeighbouringEnvironment(String host, int port) {
			invalidstate(this, host, port);
		}

		@Override
		public void setSuperEnvironment(AbstractEnvironment environment) {
			this.superEnvironment = environment;
		}

		@Override
		public void addSubEnvironment(AbstractEnvironment environment) {
			this.subEnvironments.put(environment.getAppearance(), environment);
		}

		@Override
		public void addNeighbouringEnvironment(AbstractEnvironment environment) {
			this.subEnvironments.put(environment.getAppearance(), environment);
		}

		@Override
		public Collection<EnvironmentAppearance> getSubEnvironmentAppearances() {
			return this.subEnvironments.keySet();
		}

		@Override
		public Collection<EnvironmentAppearance> getNeighbouringEnvironmentAppearances() {
			return this.neighbouringEnvironments.keySet();
		}

		@Override
		public Collection<EnvironmentAppearance> getRemoteSubEnvironmentAppearances() {
			return new HashSet<>(0);
		}

		@Override
		public Collection<EnvironmentAppearance> getRemoteNeighbouringEnvironmentAppearances() {
			return new HashSet<>(0);
		}

		@Override
		public EnvironmentAppearance getSuperEnvironmentAppearance() {
			return this.superEnvironment.getAppearance();
		}

		@Override
		public Collection<AbstractEnvironment> getSubEnvironments() {
			return this.subEnvironments.values();
		}

		@Override
		public Collection<AbstractEnvironment> getNeighbouringEnvironments() {
			return this.neighbouringEnvironments.values();
		}

		@Override
		public SocketAddress getRemoteSuperEnvironment() {
			return null;
		}

		@Override
		public Collection<SocketAddress> getRemoteSubEnvironments() {
			return new HashSet<>(0);
		}

		@Override
		public Collection<SocketAddress> getRemoteNeighbouringEnvironments() {
			return new HashSet<>(0);
		}

		@Override
		public AbstractEnvironment getSuperEnvironment() {
			return this.superEnvironment;
		}

		@Override
		public void setPort(Integer port) {
			// unused by local environments
		}

	}

	private class MixedConnectedEnvironmentState implements
			ConnectedEnvironmentState {

		private boolean superIsRemote;
		private RemoteConnectedEnvironmentState remotestate;
		private LocalConnectedEnvironmentState localstate;

		@Override
		public void sendToSuperEnvironment(Object obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void sendToSubEnvironment(EnvironmentAppearance appearance,
				Object obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void sendToNeighbouringEnvironment(
				EnvironmentAppearance appearance, Object obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setSuperEnvironment(String host, int port) {
			this.superIsRemote = true;
			this.remotestate.setSuperEnvironment(host, port);
			this.localstate.superEnvironment = null;
		}

		@Override
		public void addSubEnvironment(String host, int port) {
			this.remotestate.addSubEnvironment(host, port);
		}

		@Override
		public void addNeighbouringEnvironment(String host, int port) {
			this.remotestate.addNeighbouringEnvironment(host, port);
		}

		@Override
		public void setSuperEnvironment(AbstractEnvironment environment) {
			this.superIsRemote = false;
			this.localstate.setSuperEnvironment(environment);
			this.remotestate.superEnvironment = null;
		}

		@Override
		public void addSubEnvironment(AbstractEnvironment environment) {
			this.localstate.addSubEnvironment(environment);
		}

		@Override
		public void addNeighbouringEnvironment(AbstractEnvironment environment) {
			this.localstate.addNeighbouringEnvironment(environment);
		}

		@Override
		public void setPort(Integer port) {
			this.remotestate.setPort(port);
		}

		@Override
		public void sendToAllNeighbouringEnvironments(Object obj) {
			this.remotestate.sendToAllNeighbouringEnvironments(obj);
			this.localstate.sendToAllNeighbouringEnvironments(obj);
		}

		@Override
		public void sendToAllSubEnvironments(Object obj) {
			this.remotestate.sendToAllSubEnvironments(obj);
			this.localstate.sendToAllSubEnvironments(obj);
		}

		@Override
		public Collection<EnvironmentAppearance> getSubEnvironmentAppearances() {
			return this.localstate.getSubEnvironmentAppearances();
		}

		@Override
		public Collection<EnvironmentAppearance> getNeighbouringEnvironmentAppearances() {
			return this.localstate.getNeighbouringEnvironmentAppearances();
		}

		@Override
		public Collection<EnvironmentAppearance> getRemoteSubEnvironmentAppearances() {
			return this.remotestate.getRemoteSubEnvironmentAppearances();
		}

		@Override
		public Collection<EnvironmentAppearance> getRemoteNeighbouringEnvironmentAppearances() {
			return this.remotestate
					.getRemoteNeighbouringEnvironmentAppearances();
		}

		@Override
		public EnvironmentAppearance getSuperEnvironmentAppearance() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Collection<AbstractEnvironment> getSubEnvironments() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Collection<AbstractEnvironment> getNeighbouringEnvironments() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public SocketAddress getRemoteSuperEnvironment() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Collection<SocketAddress> getRemoteSubEnvironments() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Collection<SocketAddress> getRemoteNeighbouringEnvironments() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public AbstractEnvironment getSuperEnvironment() {
			// TODO Auto-generated method stub
			return null;
		}
	}

	private interface ConnectedEnvironmentState {

		// // **** GET APPEARNACE METHODS **** ////

		public EnvironmentAppearance getSuperEnvironmentAppearance();

		public Collection<EnvironmentAppearance> getSubEnvironmentAppearances();

		public Collection<EnvironmentAppearance> getRemoteSubEnvironmentAppearances();

		public Collection<EnvironmentAppearance> getNeighbouringEnvironmentAppearances();

		public Collection<EnvironmentAppearance> getRemoteNeighbouringEnvironmentAppearances();

		// // **** GET ENVIRONMENT METHODS **** ////

		public AbstractEnvironment getSuperEnvironment();

		public Collection<AbstractEnvironment> getSubEnvironments();

		public Collection<AbstractEnvironment> getNeighbouringEnvironments();

		// // **** GET ENVIRONMENT METHODS **** ////

		public SocketAddress getRemoteSuperEnvironment();

		public Collection<SocketAddress> getRemoteSubEnvironments();

		public Collection<SocketAddress> getRemoteNeighbouringEnvironments();

		// // **** SETTER METHODS **** ////

		public void setSuperEnvironment(String host, int port);

		public void addSubEnvironment(String host, int port);

		public void addNeighbouringEnvironment(String host, int port);

		public void setSuperEnvironment(AbstractEnvironment environment);

		public void addSubEnvironment(AbstractEnvironment environment);

		public void addNeighbouringEnvironment(AbstractEnvironment environment);

		public void setPort(Integer port);

		// // **** SEND METHODS **** ////

		public void sendToSuperEnvironment(Object obj);

		public void sendToSubEnvironment(EnvironmentAppearance appearance,
				Object obj);

		public void sendToNeighbouringEnvironment(
				EnvironmentAppearance appearance, Object obj);

		public void sendToAllNeighbouringEnvironments(Object obj);

		public void sendToAllSubEnvironments(Object obj);

	}
}
