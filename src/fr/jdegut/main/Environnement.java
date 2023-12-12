package fr.jdegut.main;

import fr.jdegut.main.agent.Agent;
import fr.jdegut.main.env.Negotiation;

import java.util.List;

public class Environnement {
	private List<Agent> agents;
	private List<Negotiation> offers;

	public Environnement(List<Agent> agents, List<Negotiation> offers) {
		this.agents = agents;
		this.offers = offers;
	}

	public List<Agent> getAgents() {
		return this.agents;
	}

	public List<Negotiation> getOffers() {
		return this.offers;
	}

	public void addAgent(Agent agent) {
		this.agents.add(agent);
	}

	public void addOffer(Negotiation offer) {
		this.offers.add(offer);
	}
}
