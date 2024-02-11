package fr.jdegut.main.strategy;

import fr.jdegut.main.agent.Agent;
import fr.jdegut.main.agent.Negotiator;

public class StrategyGRDT extends Strategy{

	@Override
	public boolean dealAccepted(float newPrice, boolean supplier) {
		if (supplier) {
			return (newPrice < this.minSupplier / 1.5);		// Cette stratégie, plus sensible, aura plus tendance
		} else {											// à se retirer d'une négociation
			return (newPrice > this.maxNegotiator * 1.5);
		}
	}

	// Stratégie stochastique : voudra toujours mettre moins de marge que son adversaire, mais a une faible
	// probabilité de proposer une énorme marge
	@Override
	public float updatePriceSupp(float previousSuppOffer, float currentNegoOffer, float previousNegoOffer) {
		float iGoCrazy = random.nextFloat();
		float previousDiff = Math.abs(currentNegoOffer - previousSuppOffer);
		float marge = 0;
		if (iGoCrazy > 0.8) {
			marge = random.nextInt((int) Math.round(previousDiff * 1.5) + 1, (int) Math.round(previousDiff * 3) + 2);
		} else {
			marge = random.nextInt((int) Math.round(previousDiff / 1.5) + 1, (int) Math.round(previousDiff / 3) + 2);
		}
		float newPrice = previousSuppOffer - marge;
		if (dealAccepted(newPrice, true)) {
			return newPrice;
		} else {
			return -1;
		}
	}

	@Override
	public float updatePriceNego(float previousSuppOffer, float currentSuppOffer, float previousNegoOffer) {
		float iGoCrazy = random.nextFloat();
		float previousDiff = Math.abs(currentSuppOffer - previousNegoOffer);
		float gain = 0;
		if (iGoCrazy > 0.85) {
			gain = random.nextInt((int) Math.round(previousDiff * 1.5), (int) Math.round(previousDiff * 3));
		} else {
			gain = random.nextInt((int) Math.round(previousDiff / 3), (int) Math.round(previousDiff / 1.5));
		}
		float newPrice = previousNegoOffer + gain;
		if (dealAccepted(newPrice, false)) {
			return newPrice;
		} else {
			return -1;
		}
	}
}
