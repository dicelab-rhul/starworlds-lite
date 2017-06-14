package uk.ac.rhul.cs.dice.starworlds.test;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Observable;
import java.util.Observer;

import uk.ac.rhul.cs.dice.starworlds.utils.inet.INetServer;
import uk.ac.rhul.cs.dice.starworlds.utils.inet.INetSlave;
import uk.ac.rhul.cs.dice.starworlds.utils.inet.sendreceive.INetByteReceiver;
import uk.ac.rhul.cs.dice.starworlds.utils.inet.sendreceive.INetByteSender;

public class JoseTest implements Observer {

	public static void main(String[] args) {
		JoseTest t = new JoseTest();
		ViewServer s1 = t.new ViewServer(10001);
		ViewServer s2 = t.new ViewServer(10002); //delete

		try {
			SocketAddress addr = s1.connect("localhost", 10002);
			s2.getClientAddresses().forEach((SocketAddress s) -> {
				s2.getSlave(s).addObserver(t);
			});
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

	public class ViewServer extends INetServer {

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

	public class ViewSlave extends INetSlave {

		public ViewSlave(Socket socket) throws IOException {
			super(socket, new INetByteSender(socket.getOutputStream()),
					new INetByteReceiver(socket.getInputStream()));
		}

	}
}
