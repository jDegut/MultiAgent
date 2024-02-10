package fr.jdegut.main.strategy;

import fr.jdegut.main.agent.Agent;
import fr.jdegut.main.agent.Supplier;

public class StrategyBoomerang extends Strategy{

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
		float delta = Math.abs(currentNegoOffer - previousSuppOffer);	// Différence précédente par rapport à la proposition du Nego
		float randomDivFactor = random.nextInt(5, 30) / 10;	// Ajout d'aléatoire dans le delta pour éviter de divulguer la stratégie
		float newPrice = previousSuppOffer - (delta / randomDivFactor);
		if (dealAccepted(newPrice, true)) {
			return newPrice;
		} else {
			return -1;
		}
	}

	@Override
	public float updatePriceNego(float previousSuppOffer, float currentSuppOffer, float previousNegoOffer) {
		float delta = Math.abs(currentSuppOffer - previousNegoOffer);
		float randomDivFactor = random.nextInt(4, 25) / 10; // Delta moins important car le Négociateur a tendance à moins augmenter ses offres
		float newPrice = previousNegoOffer - (delta / randomDivFactor);
		if (dealAccepted(newPrice, false)) {
			return newPrice;
		} else {
			return -1;
		}
	}
}
