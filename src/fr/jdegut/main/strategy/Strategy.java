package fr.jdegut.main.strategy;

import fr.jdegut.main.agent.Agent;
import fr.jdegut.main.env.Negotiation;

import java.util.Random;

public abstract class Strategy {
	protected final Random random = new Random();
	public float minSupplier;
	public float maxNegotiator;

	public abstract boolean dealAccepted(float newPrice, boolean supplier);

	public abstract float updatePriceSupp(float previousSuppOffer, float currentNegoOffer, float previousNegoOffer);
	public abstract float updatePriceNego(float previousSuppOffer, float currentSuppOffer, float previousNegoOffer);
}
