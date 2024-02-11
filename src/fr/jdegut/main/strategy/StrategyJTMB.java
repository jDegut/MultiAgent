package fr.jdegut.main.strategy;

import fr.jdegut.main.agent.Agent;
import fr.jdegut.main.agent.Supplier;

public class StrategyJTMB extends Strategy{

	@Override
	public boolean dealAccepted(float newPrice, boolean supplier) {
		if (supplier) {
			return (newPrice < this.minSupplier / 4);
		} else {
			return (newPrice > this.maxNegotiator * 4);
		}
	}

	// Propose des grosses marges, tente de prédire les offres de son adversaire
	@Override
	public float updatePriceSupp(float previousSuppOffer, float currentNegoOffer, float previousNegoOffer) {
		float negoDiff = Math.abs(previousNegoOffer - currentNegoOffer); // Différence réelle sur les offres du negotiator
		float diffPrediction = random.nextInt(5, 26) / 100;
		float negoPredict = (float) Math.abs(previousNegoOffer - (previousNegoOffer * (1 + diffPrediction)));	// Différence prédite de 20%
		float previousDiff = Math.abs(previousSuppOffer - currentNegoOffer);
		float marge = 0;
		if (negoDiff >= negoPredict) {
			marge = random.nextInt((int) Math.round(previousDiff * 2) + 1, (int) Math.round(previousDiff * 4) + 2);
		} else {
			marge = random.nextInt((int) Math.round(previousDiff * 0.8) + 1, (int) Math.round(previousDiff * 1.2) + 2);
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
		float suppDiff = Math.abs(previousSuppOffer - currentSuppOffer); // Différence réelle sur les offres du negotiator
		float diffPrediction = random.nextInt(5, 26) / 100;
		float suppPredict = (float) Math.abs(previousSuppOffer - (previousSuppOffer * (1 + diffPrediction)));	// Différence prédite de 20%
		float previousDiff = Math.abs(previousNegoOffer - currentSuppOffer);
		float marge = 0;
		if (suppDiff >= suppPredict) {
			marge = random.nextInt((int) Math.round(previousDiff * 2), (int) Math.round(previousDiff * 4));
		} else {
			marge = random.nextInt((int) Math.round(previousDiff * 0.8), (int) Math.round(previousDiff * 1.2));
		}
		float newPrice = previousNegoOffer + marge;
		if (dealAccepted(newPrice, false)) {
			return newPrice;
		} else {
			return -1;
		}
	}
}
