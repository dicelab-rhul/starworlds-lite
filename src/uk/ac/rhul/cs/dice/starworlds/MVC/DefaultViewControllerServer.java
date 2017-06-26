package uk.ac.rhul.cs.dice.starworlds.MVC;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;

import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.Universe;
import uk.ac.rhul.cs.dice.starworlds.utils.inet.INetServer;
import uk.ac.rhul.cs.dice.starworlds.utils.inet.INetSlave;
import uk.ac.rhul.cs.dice.starworlds.utils.inet.sendreceive.INetByteReceiver;
import uk.ac.rhul.cs.dice.starworlds.utils.inet.sendreceive.INetByteSender;

/**
 * A concrete implementation of {@link ViewController} that allows multiple
 * remote Views to connect. This class illustrates the use of a
 * {@link ViewController}.
 * 
 * @author Ben
 *
 */
public class DefaultViewControllerServer extends ViewControllerServer {

	public DefaultViewControllerServer(Universe universe, int port) {
		super(new INetServer(port) {
			@Override
			public INetSlave newSlave(Socket socket) {
				try {
					return new INetSlave(socket, new INetByteSender(
							socket.getOutputStream()), new INetByteReceiver(
							socket.getInputStream())) {
					};
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
		}, universe);
	}

	/**
	 * Prints the sender and the message that was received and then replies.
	 */
	@Override
	public void messageReceived(SocketAddress viewaddr, Object message) {
		System.out.println("View: " + viewaddr + System.lineSeparator()
				+ "    Sent: " + new String((byte[]) message));
		this.send(viewaddr, "Here is some information!".getBytes());
	}
}
