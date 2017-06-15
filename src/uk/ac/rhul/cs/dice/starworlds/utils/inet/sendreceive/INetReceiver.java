package uk.ac.rhul.cs.dice.starworlds.utils.inet.sendreceive;

import java.io.InputStream;

public interface INetReceiver {

	public Object receive();

	public void setInputStream(InputStream in);
}
