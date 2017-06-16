package uk.ac.rhul.cs.dice.starworlds.test;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

import uk.ac.rhul.cs.dice.starworlds.utils.inet.INetServer;
import uk.ac.rhul.cs.dice.starworlds.utils.inet.INetSlave;
import uk.ac.rhul.cs.dice.starworlds.utils.inet.sendreceive.INetObjectReceiver;
import uk.ac.rhul.cs.dice.starworlds.utils.inet.sendreceive.INetObjectSender;

public class LamportClockTest implements Observer {

	public static void main(String[] args) throws Exception {
		LamportClockTest test = new LamportClockTest();
		INetServer s1 = createServer(10001);
		INetServer s2 = createServer(10002);
		s1.addObserver(test);
		s2.addObserver(test);
		SocketAddress a1 = s1.connect("localhost", 10002);
		s1.send(a1, "a1");
		SocketAddress a2 = (SocketAddress) s2.getClientAddresses().toArray()[0];
		s2.send(a2, "a2");
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println(o + " " + arg);
	}

	private static INetServer createServer(int port) {
		return new INetServer(port) {
			@Override
			public INetSlave newSlave(Socket socket) {
				return new INetSlave(socket, new INetObjectSender(socket),
						new INetObjectReceiver(socket)) {
				};
			}
		};
	}
}
