package fr.jdegut.main.env;

import fr.jdegut.main.agent.Agent;
import fr.jdegut.main.agent.Negotiator;
import fr.jdegut.main.agent.Supplier;
import fr.jdegut.main.env.Negotiation;
import fr.jdegut.main.env.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Environnement {

	public static LocalDateTime startDate;

	private final List<Agent> agents;
	private final List<Supplier> suppliers;
	private final List<Negotiator> negotiators;
	private final List<Negotiation> board;

	public Environnement() {
		this.agents = new ArrayList<>();
		this.suppliers = new ArrayList<>();
		this.negotiators = new ArrayList<>();
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
		if(agent instanceof Supplier supplier) {
			this.suppliers.add(supplier);
		} else if(agent instanceof Negotiator negotiator) {
			this.negotiators.add(negotiator);
		}
	}

	public void addOfferToBoard(Supplier supplier, Service offer) {
		Negotiation negotiation = new Negotiation(supplier, offer, LocalDateTime.now());
		this.board.add(negotiation);
	}

	public List<Negotiation> getBoard() {
		return this.board;
	}


	public List<Supplier> getSuppliers() {
		return this.suppliers;
	}
}
