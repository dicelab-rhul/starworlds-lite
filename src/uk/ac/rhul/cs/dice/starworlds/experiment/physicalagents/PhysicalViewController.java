package uk.ac.rhul.cs.dice.starworlds.experiment.physicalagents;

import java.util.HashMap;
import java.util.Observable;

import uk.ac.rhul.cs.dice.starworlds.MVC.AbstractViewController;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.entities.avatar.AbstractAvatarMind;
import uk.ac.rhul.cs.dice.starworlds.entities.avatar.AbstractSelfishAvatarMind;
import uk.ac.rhul.cs.dice.starworlds.entities.avatar.link.ScannerAvatarLink;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.Universe;
import uk.ac.rhul.cs.dice.starworlds.utils.Pair;

public class PhysicalViewController extends AbstractViewController {

	private static HashMap<String, Pair<Integer, Integer>> inputmap = new HashMap<>();
	static {
		inputmap.put("a", new Pair<>(-1, 0));
		inputmap.put("w", new Pair<>(0, -1));
		inputmap.put("s", new Pair<>(0, 1));
		inputmap.put("d", new Pair<>(1, 0));
	}

	public PhysicalViewController(Universe universe) {
		super(universe);
		PhysicalUniverse pu = (PhysicalUniverse) universe;
		pu.addObserver(this);
		@SuppressWarnings("unchecked")
		// unchecked but the generic type is always AbstractEnvironmentalAction.
		ScannerAvatarLink<AbstractEnvironmentalAction, AbstractAvatarMind<AbstractEnvironmentalAction>> avatarlink = new ScannerAvatarLink<AbstractEnvironmentalAction, AbstractAvatarMind<AbstractEnvironmentalAction>>(
				(AbstractAvatarMind<AbstractEnvironmentalAction>) pu.getState()
						.getAvatars().stream().findAny().get().getMind());
		// add mappings
		try {
			avatarlink.addMapping("a", MoveAction.class, int.class, int.class);
			avatarlink.addMapping("w", MoveAction.class, int.class, int.class);
			avatarlink.addMapping("s", MoveAction.class, int.class, int.class);
			avatarlink.addMapping("d", MoveAction.class, int.class, int.class);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		// user input thread
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					String input = avatarlink.read();
					System.out.println("GOT USER INPUT: " + input);
					for (Character c : input.toCharArray()) {
						String in = c.toString();
						System.out.println("IN: " + in);
						if (avatarlink.containsKey(in)) {
							Pair<Integer, Integer> pair = inputmap.get(in);
							avatarlink.decide(in, pair.getFirst(),
									pair.getSecond());
						}
					}
				}
			}
		}).start();
	}

	@Override
	public void update(Observable o, Object arg) {
		((PhysicalAmbient) universe.getState()).printGrid();
	}
}
