package uk.ac.rhul.cs.dice.starworlds.utils.inet.sendreceive;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class INetByteReceiver implements INetReceiver {

	private static final int DEFAULTREADSIZE = 64;

	private InputStream in;
	private byte[] readarray;

	public INetByteReceiver() {
		readarray = new byte[DEFAULTREADSIZE];
	}

	public INetByteReceiver(InputStream in) {
		this.in = in;
		readarray = new byte[DEFAULTREADSIZE];
	}

	public INetByteReceiver(InputStream in, int readsize) {
		this.in = in;
		this.readarray = new byte[readsize];
	}

	@Override
	public byte[] receive() {
		try {
			int read = in.read(readarray);
			return Arrays.copyOfRange(readarray, 0, read);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void setInputStream(InputStream in) {
		this.in = in;
	}
}
