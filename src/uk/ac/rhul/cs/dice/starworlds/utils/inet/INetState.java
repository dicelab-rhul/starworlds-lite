package uk.ac.rhul.cs.dice.starworlds.utils.inet;

import java.io.Serializable;
import java.net.SocketAddress;

public interface INetState {

	public void send(Serializable message, SocketAddress... addresses);

	public boolean isServer();

}
