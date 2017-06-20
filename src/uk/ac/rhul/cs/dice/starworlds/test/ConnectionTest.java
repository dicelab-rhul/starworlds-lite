package uk.ac.rhul.cs.dice.starworlds.test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

import uk.ac.rhul.cs.dice.starworlds.utils.inet.INetServer;
import uk.ac.rhul.cs.dice.starworlds.utils.inet.INetSlave;
import uk.ac.rhul.cs.dice.starworlds.utils.inet.sendreceive.INetByteReceiver;
import uk.ac.rhul.cs.dice.starworlds.utils.inet.sendreceive.INetByteSender;

public class ConnectionTest implements Observer {

	private static String host = "localhost";
	private static Integer port = 80;
	private static Integer localport = 10001;

	public static void main(String[] args) throws UnknownHostException {
		System.out.println("MY ADDRESS: "
				+ InetAddress.getLocalHost().getHostAddress());
		ViewServer s1 = new ViewServer(localport);
		ViewServer s2 = new ViewServer(port);
		try {
			SocketAddress addr = s1.connect(host, port);
			s1.send(addr, "hello".getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Observable o, Object obj) {
		byte[] m = (byte[]) obj;
		System.out.println(new String(m));
	}

	public static class ViewServer extends INetServer {

		public ViewServer(int port) {
			super(port);
		}

		@Override
		public INetSlave newSlave(Socket socket) {
			try {
				return new ViewSlave(socket);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		public void send(SocketAddress addr, Object message) {
			this.slaves.get(addr).send(message);
		}
	}

	public static class ViewSlave extends INetSlave {

		public ViewSlave(Socket socket) throws IOException {
			super(socket, new INetByteSender(socket.getOutputStream()),
					new INetByteReceiver(socket.getInputStream()));
		}

	}
}
