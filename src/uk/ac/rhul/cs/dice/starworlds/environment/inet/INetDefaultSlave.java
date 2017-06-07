package uk.ac.rhul.cs.dice.starworlds.environment.inet;

import java.io.IOException;
import java.net.Socket;

import uk.ac.rhul.cs.dice.starworlds.utils.inet.INetSlave;

public class INetDefaultSlave extends INetSlave {

	public INetDefaultSlave(Socket socket, Class<?> expectedin)
			throws IOException {
		super(socket, new INetDefaultSender(socket), new INetDefaultReceiver(
				socket, expectedin));
	}
}
