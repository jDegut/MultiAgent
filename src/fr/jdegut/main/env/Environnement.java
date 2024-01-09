package fr.jdegut.main.env;

import fr.jdegut.main.agent.Agent;
import fr.jdegut.main.agent.Supplier;
import fr.jdegut.main.env.Negotiation;
import fr.jdegut.main.env.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Environnement {

	public static LocalDateTime startDate;

	private final List<Agent> agents;
	private final List<Service> board;

	public Environnement() {
		this.agents = new ArrayList<>();
		this.board = new ArrayList<>();
	}

	public void run() {

		startDate = LocalDateTime.now();

		for(Agent agent : this.agents) {
			new Thread(agent).start();
		}
	}

	public List<Agent> getAgents() {
		return this.agents;
	}

	public void addAgent(Agent agent) {
		this.agents.add(agent);
		agent.setEnv(this);
	}

	public void addOfferToBoard(Service offer) {
		this.board.add(offer);
	}
}
