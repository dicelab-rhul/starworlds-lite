package uk.ac.rhul.cs.dice.starworlds.test;

import uk.ac.rhul.cs.dice.starworlds.entities.avatar.link.ScannerAvatarLink;

public class ScannerTest {

	public static void main(String[] args) {
		ScannerAvatarLink<?, ?> s = new ScannerAvatarLink<>(null);
		while (true) {
			s.read();
		}
	}
}
