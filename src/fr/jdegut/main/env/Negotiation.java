package fr.jdegut.main.env;

import fr.jdegut.main.agent.Agent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Negotiation {

	private final Service service;
	private final LocalDateTime date;
	private final List<Agent> agents;
	private final LinkedList<Message> offers;

	public Negotiation(Service service, LocalDateTime date) {
		this.service = service;
		this.date = date;
		this.agents = new ArrayList<>();
		this.offers = new LinkedList<>();
	}

	public Service getService() {
		return this.service;
	}

	public LocalDateTime getDate() {
		return this.date;
	}

	public List<Agent> getAgents() {
		return this.agents;
	}

	public void addAgent(Agent agent) {
		this.agents.add(agent);
	}

	public LinkedList<Message> getOffers() {
		return this.offers;
	}

	public void addOffer(Message offer) {
		this.offers.add(offer);
	}

}
