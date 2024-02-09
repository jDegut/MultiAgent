package fr.jdegut.main.env;

import fr.jdegut.main.agent.Negotiator;
import fr.jdegut.main.agent.Supplier;

import java.time.LocalDateTime;
import java.util.*;

public class Negotiation {

	private final Ticket ticket;
	private final Supplier supplier;
	private final Negotiator negotiator;
	private float agreedPrice;

	public Negotiation(Supplier supplier, Negotiator negotiator, Ticket ticket) {
		this.ticket = ticket;
		this.supplier = supplier;
		this.negotiator = negotiator;
		this.agreedPrice = 0;
	}

	public boolean negotiate() {
		// TODO
		return true;
	}

	public Ticket getService() {
		return this.ticket;
	}

	public Supplier getSupplier() {
		return this.supplier;
	}

	public float getAgreedPrice() {
		return this.agreedPrice;
	}
}
