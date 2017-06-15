package uk.ac.rhul.cs.dice.starworlds.utils.inet.sendreceive;

import java.io.OutputStream;

public interface INetSender {

	public abstract void send(Object message);

	public void setOutputStream(OutputStream out);

}
