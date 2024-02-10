package fr.jdegut.main.strategy;

import fr.jdegut.main.agent.Agent;
import fr.jdegut.main.env.Negotiation;

import java.util.Random;

public abstract class Strategy {
	protected final Random random = new Random();
	public float minSupplier;		// Prix min qu'un supplier ne voudra jamais aller en-dessous
	public float maxNegotiator;		// Prix max qu'un negotiator ne voudra jamais aller au-dessus

	public abstract boolean dealAccepted(float newPrice, boolean supplier);
	// Check si minSupplier et maxNegotiator ne sont pas dépassés

	// Update de la nouvelle offre si on est un Supplier
	public abstract float updatePriceSupp(float previousSuppOffer, float currentNegoOffer, float previousNegoOffer);
	// Update de la nouvelle offre si on est Negotiator
	public abstract float updatePriceNego(float previousSuppOffer, float currentSuppOffer, float previousNegoOffer);
}
