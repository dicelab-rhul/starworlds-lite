package uk.ac.rhul.cs.dice.starworlds.initialisation;

import java.util.ArrayList;
import java.util.List;

import uk.ac.rhul.cs.dice.starworlds.environment.interaction.EnvironmentConnectionManager;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.AbstractConnectedEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.AbstractWorld;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.WorldNode;
import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.tree.Node;
import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.visitor.Acceptor;
import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.visitor.Visitor;

/**
 * A {@link Visitor} class that {@link Visitor#visit(Acceptor) visits}
 * {@link WorldNode}s and calls the
 * {@link AbstractConnectedEnvironment#initialiseEnvironmentConnections(java.util.Collection, java.util.Collection)}
 * method. See this method for details. This {@link Visitor} is used in the
 * {@link WorldDeployer} to initialise the {@link AbstractWorld World}.
 * 
 * @author Ben Wilkins
 *
 */
public class ConnectionInitialisationVisitor implements
		Visitor<AbstractConnectedEnvironment> {

	private List<Thread> waitingThreads = new ArrayList<>();

	@Override
	public void visit(Acceptor<AbstractConnectedEnvironment> acceptor) {
		WorldNode node = (WorldNode) acceptor;
		node.getValue().initialiseEnvironmentConnections(
				Node.getValuesFrom(node.getChildren()),
				Node.getValuesFrom(node.getNeighbours()));
		if (node.getValue().getConnectedEnvironmentManager()
				.expectingRemoteConnections()) {
			System.out.println("CREATING WAITING THREAD");
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					EnvironmentConnectionManager m = node.getValue()
							.getConnectedEnvironmentManager();
					System.out.println("WAITING...");
					while (!m.reachedExpectedRemoteConnections())
						;
					System.out.println("...DONE WAITING");
				}
			});
			waitingThreads.add(t);
			t.start();
		}
	}

	public void waitFromRemoteConnections() throws InterruptedException {
		System.out.println("WAITING FOR EXPECTED REMOTE CONNECTIONS...");
		for (Thread t : waitingThreads) {
			t.join();
		}
		System.out.println("...DONE");
	}
}
