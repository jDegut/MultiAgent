package fr.jdegut.main;

import fr.jdegut.main.agent.Agent;
import fr.jdegut.main.agent.Supplier;
import fr.jdegut.main.env.Negotiation;
import fr.jdegut.main.env.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Environnement {
	private final List<Agent> agents;
	private final Random random;

	public Environnement() {
		this.agents = new ArrayList<>();
		this.random = new Random();
	}

	public void run() {

		Supplier s1 = new Supplier();

		for(Agent agent : this.agents) {
			new Thread(agent).start();
		}
	}

	public List<Agent> getAgents() {
		return this.agents;
	}

	public void addAgent(Agent agent) {
		this.agents.add(agent);
	}
}
