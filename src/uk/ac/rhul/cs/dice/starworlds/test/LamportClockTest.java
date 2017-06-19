package uk.ac.rhul.cs.dice.starworlds.test;

import java.net.Socket;
import java.net.SocketAddress;
import java.util.Observable;
import java.util.Observer;

import uk.ac.rhul.cs.dice.starworlds.utils.Pair;
import uk.ac.rhul.cs.dice.starworlds.utils.inet.INetServer;
import uk.ac.rhul.cs.dice.starworlds.utils.inet.INetSlave;
import uk.ac.rhul.cs.dice.starworlds.utils.inet.sendreceive.INetObjectReceiver;
import uk.ac.rhul.cs.dice.starworlds.utils.inet.sendreceive.INetObjectSender;

public class LamportClockTest implements Observer {

	public static void main(String[] args) throws Exception {
		LamportClockTest test = new LamportClockTest();
		TimeServer s1 = createServer(10001);
		TimeServer s2 = createServer(10002);
		s1.addObserver(test);
		s2.addObserver(test);
		SocketAddress a1 = s1.connect("localhost", 10002);
		SocketAddress a2 = (SocketAddress) s2.getClientAddresses().toArray()[0];
		send(s1, a1);
		send(s2, a2);
	}

	private static void send(TimeServer server, SocketAddress addr) {
		server.time = server.time++;
		int timestamp = server.time;
		server.send(addr, timestamp);
	}

	@Override
	public void update(Observable o, Object arg) {
		((TimeServer) o).time = Math
				.max((Integer) ((Pair<?, ?>) arg).getSecond(),
						((TimeServer) o).time) + 1;
	}

	private static TimeServer createServer(int port) {
		return new TimeServer(port);
	}

	private static class TimeServer extends INetServer {

		Integer time = 0;

		public TimeServer(int port) {
			super(port);
		}

		@Override
		public INetSlave newSlave(Socket socket) {
			return new INetSlave(socket, new INetObjectSender(socket),
					new INetObjectReceiver(socket)) {
			};
		}
	}
}
