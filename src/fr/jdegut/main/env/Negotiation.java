package fr.jdegut.main.env;

import fr.jdegut.main.agent.Negotiator;
import fr.jdegut.main.agent.Supplier;
import fr.jdegut.main.strategy.Strategy;

import java.time.LocalDateTime;
import java.util.*;

public class Negotiation {

	public float previousNegoOffer;
	public float previousSuppOffer;
	private final Ticket ticket;
	private final Supplier supplier;
	private final Negotiator negotiator;
	private float agreedPrice;
	public float supplierMin;
	public float negotiatorMax;


	public Negotiation(Supplier supplier, Negotiator negotiator, Ticket ticket) {
		this.ticket = ticket;
		this.supplier = supplier;
		this.negotiator = negotiator;
		this.agreedPrice = 0;
	}

	public boolean negotiate() {
		Strategy negoS = this.negotiator.getStrategy();
		Strategy suppS = this.supplier.getStrategy();
		suppS.minSupplier = this.ticket.price;
		negoS.maxNegotiator = this.negotiator.initialOfferToSupplier;
		this.supplierMin = this.ticket.price;
		this.negotiatorMax = this.negotiator.initialOfferToSupplier;

		while (this.negotiatorMax < this.supplierMin) {
			if (this.agreedPrice == -1) {
				return false;
			}
			this.negotiatorMax = negoS.updatePriceNego(this.previousSuppOffer, this.supplierMin, this.previousNegoOffer);
			this.previousNegoOffer = this.negotiatorMax;

			this.supplierMin = suppS.updatePriceSupp(this.previousNegoOffer, this.negotiatorMax, this.previousNegoOffer);
			this.previousSuppOffer = this.supplierMin;

		}
		this.agreedPrice = this.negotiatorMax;
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
