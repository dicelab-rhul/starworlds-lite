package uk.ac.rhul.cs.dice.starworlds.environment.inet;

import java.net.Socket;

import uk.ac.rhul.cs.dice.starworlds.utils.inet.sendreceive.INetObjectSender;

public class INetDefaultSender extends INetObjectSender {

	public INetDefaultSender(Socket socket) {
		super(socket);
	}

}
