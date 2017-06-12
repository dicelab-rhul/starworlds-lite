package uk.ac.rhul.cs.dice.starworlds.environment.subscriber;

import java.net.SocketAddress;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.AbstractSensor;

public class INetSubscriber extends AbstractSubscriber {

	private Map<SocketAddress, Map<String, Set<Class<? extends AbstractSensor>>>> remoteAgents;

	public INetSubscriber() {
		remoteAgents = new HashMap<>();
	}

	public void addRemoteAgent(String agentid,
			Set<Class<? extends AbstractSensor>> agentsensors,
			SocketAddress remote) {
		remoteAgents.putIfAbsent(remote, new HashMap<>());
		remoteAgents.get(remote).put(agentid, agentsensors);
	}

	public HashMap<String, Set<Class<? extends AbstractSensor>>> getLocallySubscribedAgents() {
		HashMap<String, Set<Class<? extends AbstractSensor>>> result = new HashMap<>();
		subscribedSensors
				.forEach((
						String body,
						Map<Class<? extends AbstractSensor>, AbstractSensor> sensors) -> {
					result.put(body, new HashSet<>(sensors.keySet()));
				});
		return result;
	}

	public Map<SocketAddress, Map<String, Set<Class<? extends AbstractSensor>>>> getRemoteAgents() {
		return remoteAgents;
	}
}
