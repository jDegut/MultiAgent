package fr.jdegut.main.env;

import fr.jdegut.main.agent.Negotiator;
import fr.jdegut.main.agent.Supplier;

import java.time.LocalDateTime;
import java.util.*;

public class Negotiation {

	private final Ticket ticket;
	private final LocalDateTime date;
	private final Supplier supplier;
	private final List<Negotiator> negotiators;
	private final Map<Negotiator, Float> offers;

	public Negotiation(Supplier supplier, Ticket ticket, LocalDateTime date) {
		this.ticket = ticket;
		this.date = date;
		this.supplier = supplier;
		this.negotiators = new ArrayList<>();
		this.offers = new HashMap<>();
	}

	public Negotiation(Supplier supplier, Ticket ticket) {
		this(supplier, ticket, LocalDateTime.now());
	}

	public Ticket getService() {
		return this.ticket;
	}

 	public Map<Negotiator, Float> getOffers() {
		return this.offers;
	}

	public void addOffer(Negotiator negotiator, Float offer) {
		this.offers.put(negotiator, offer);
	}

	public Supplier getSupplier() {
		return this.supplier;
	}
}
