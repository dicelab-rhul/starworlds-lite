package uk.ac.rhul.cs.dice.starworlds.utils.inet.sendreceive;

import java.io.IOException;
import java.io.OutputStream;

public class INetByteSender implements INetSender {

	private OutputStream out;

	public INetByteSender() {
	}

	public INetByteSender(OutputStream out) {
		this.out = out;
	}

	@Override
	public void send(Object message) {
		send((byte[]) message);
	}

	public void send(byte[] message) {
		try {
			out.write(message);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setOutputStream(OutputStream out) {
		this.out = out;
	}
}
