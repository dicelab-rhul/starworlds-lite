package uk.ac.rhul.cs.dice.starworlds.test;

import java.util.Observable;
import java.util.Observer;

import uk.ac.rhul.cs.dice.starworlds.MVC.DefaultViewControllerServer;
import uk.ac.rhul.cs.dice.starworlds.utils.inet.INetSlave;
import uk.ac.rhul.cs.dice.starworlds.utils.inet.sendreceive.INetByteReceiver;
import uk.ac.rhul.cs.dice.starworlds.utils.inet.sendreceive.INetByteSender;

/**
 * Example of communication between a (fake) View and the
 * {@link DefaultViewControllerServer}.
 * 
 * @author Ben
 *
 */
public class MVCExample implements Observer {

	public static void main(String[] args) {
		DefaultViewControllerServer cont = new DefaultViewControllerServer(
				null, 10001);
		INetSlave fakeView = new INetSlave("localhost", 10001,
				new INetByteSender(), new INetByteReceiver()) {
		};
		fakeView.addObserver(new MVCExample());
		new Thread(fakeView).start();
		fakeView.send("Requesting information please!".getBytes());
	}

	@Override
	public void update(Observable o, Object message) {
		System.out
				.println("View has received: " + new String((byte[]) message));

	}
}
