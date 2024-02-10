package fr.jdegut.main.strategy;

import fr.jdegut.main.agent.Agent;
import fr.jdegut.main.agent.Negotiator;

public class StrategyGRDT extends Strategy{

	@Override
	public boolean dealAccepted(float newPrice, boolean supplier) {
		if (supplier) {
			return (newPrice < this.minSupplier / 2);
		} else {
			return (newPrice > this.maxNegotiator * 2);
		}
	}
	@Override
	public float updatePriceSupp(float previousSuppOffer, float currentNegoOffer, float previousNegoOffer) {
		float gain = random.nextFloat(1, (previousSuppOffer / 5) + 1);
		return previousSuppOffer - gain;
	}

	@Override
	public float updatePriceNego(float previousSuppOffer, float currentSuppOffer, float previousNegoOffer) {
		float gain = random.nextFloat(1, (previousNegoOffer / 5) + 1);
		return previousNegoOffer + gain;
	}
}
