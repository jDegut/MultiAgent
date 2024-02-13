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
	public float supplierMin;
	public float negotiatorMax;


	public Negotiation(Supplier supplier, Negotiator negotiator, Ticket ticket) {
		this.ticket = ticket;
		this.supplier = supplier;
		this.negotiator = negotiator;
	}

	// Simule une negociation entre un supplier et un negotiator, chacun suivant sa propre stratégie
	// Renvoie seulement un booléen indiquant si la négociation a réussi ou non, on trouve le prix d'accord via getAgreedPrice()
	public boolean negotiate() {
		Strategy negoS = this.negotiator.getStrategy();		// Chargement des stratégies
		Strategy suppS = this.supplier.getStrategy();
		suppS.minSupplier = this.ticket.price;			// Prix min et max que les negociants ne veulent pas dépasser
		negoS.maxNegotiator = this.negotiator.initialOfferToSupplier;
		this.supplierMin = this.ticket.price;
		if (this.negotiator.coalition) {	// Si le Negotiator fait partie de la coalition, le Supplier réduit de 10% le prix minimal qu'il veut recevoir.
			this.supplierMin *= (1 - this.negotiator.getEnv().percentageCoalition / 3);
		}
		this.negotiatorMax = this.negotiator.initialOfferToSupplier;

		while (this.negotiatorMax < this.supplierMin) {
			if (this.negotiatorMax == -1 || this.supplierMin == -1) {		// Si l'un des deux protagonistes ne veut plus négocier
				return false;
			}
			this.negotiatorMax = negoS.updatePriceNego(this.previousSuppOffer, this.supplierMin, this.previousNegoOffer);
			this.previousNegoOffer = this.negotiatorMax;		// Mise à jour des prix pour que la négociation aboutisse

			this.supplierMin = suppS.updatePriceSupp(this.previousNegoOffer, this.negotiatorMax, this.previousNegoOffer);
			this.previousSuppOffer = this.supplierMin;			// Mise à jour des prix pour que la négociation aboutisse

		}
		return true;
	}

	public Ticket getService() {
		return this.ticket;
	}

	public Supplier getSupplier() {
		return this.supplier;
	}

	public float getAgreedPrice() {
		return this.negotiatorMax;
	}
}
