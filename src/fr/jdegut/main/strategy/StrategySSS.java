package fr.jdegut.main.strategy;

import fr.jdegut.main.agent.Agent;
import fr.jdegut.main.agent.Supplier;

public class StrategySSS extends Strategy {


	@Override
	public boolean dealAccepted(float newPrice, boolean supplier) {
		if (supplier) {
			return (newPrice < this.minSupplier / 1.5);
		} else {
			return (newPrice > this.maxNegotiator * 1.5);
		}
	}

	// Smart Secure Safe
	// A pour stratégie de ne prendre que des marges moins importantes que son interlocuteur
	@Override
	public float updatePriceSupp(float previousSuppOffer, float currentNegoOffer, float previousNegoOffer) {
		float negoDiff = Math.abs(currentNegoOffer - previousNegoOffer);
		float divFactor = random.nextInt(50, 90) / 100;
		float newPrice = previousSuppOffer - (negoDiff * divFactor);
		if (dealAccepted(newPrice, true)) {
			return newPrice;
		} else {
			return -1;
		}
	}

	@Override
	public float updatePriceNego(float previousSuppOffer, float currentSuppOffer, float previousNegoOffer) {
		float suppDiff = Math.abs(currentSuppOffer - previousSuppOffer);
		float divFactor = random.nextInt(50, 90) / 100;
		float newPrice = previousNegoOffer - (suppDiff * divFactor);
		if (dealAccepted(newPrice, false)) {
			return newPrice;
		} else {
			return -1;
		}
	}

}
