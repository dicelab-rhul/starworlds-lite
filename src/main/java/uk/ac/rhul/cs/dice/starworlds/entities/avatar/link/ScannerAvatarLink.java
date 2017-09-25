package uk.ac.rhul.cs.dice.starworlds.entities.avatar.link;

import java.io.InputStream;
import java.util.Scanner;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.entities.avatar.AbstractAvatarMind;

public class ScannerAvatarLink<A extends Action, M extends AbstractAvatarMind<A>>
		extends AbstractAvatarLink<String, A, M> {

	private Scanner scanner;

	public ScannerAvatarLink(M mind) {
		super(mind);
		scanner = new Scanner(System.in);
	}

	public ScannerAvatarLink(M mind, InputStream in) {
		super(mind);
		scanner = new Scanner(in);
	}

	public Boolean readAndDecideEmpty() {
		if (scanner.hasNext()) {
			String in = scanner.next();
			Class<?> c = this.get(in);
			if (c != null) {
				return this.decide(in);
			}
		}
		return false;
	}

	public String read() {
		if (scanner.hasNext()) {
			return scanner.next();
		}
		return null;
	}
}
