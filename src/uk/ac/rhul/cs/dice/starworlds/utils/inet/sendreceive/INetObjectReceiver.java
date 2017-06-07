package uk.ac.rhul.cs.dice.starworlds.utils.inet.sendreceive;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class INetObjectReceiver implements INetReceiver {

	protected ObjectInputStream in;
	protected Class<?> expectedinclass;

	
	public INetObjectReceiver(Socket socket, Class<?> expectedinclass) {
		try {
			INetObjectReceiver.this.in = new ObjectInputStream(
					socket.getInputStream());
			this.expectedinclass = expectedinclass;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object receive() {
		try {
			return this.in.readObject();
		} catch (ClassNotFoundException | IOException e) {
			System.err.println(this + " RECEIVED INVALID DATA");
			return null;
		}
	}
	
	public Class<?> getExpectedinclass() {
		return expectedinclass;
	}

}
