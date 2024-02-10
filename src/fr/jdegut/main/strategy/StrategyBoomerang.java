package fr.jdegut.main.strategy;

import fr.jdegut.main.agent.Agent;
import fr.jdegut.main.agent.Supplier;

public class StrategyBoomerang extends Strategy{
	private double oldPrice = -1;

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
		float delta = Math.abs(currentNegoOffer - previousSuppOffer);
		return previousSuppOffer - (delta / 2);
	}

	@Override
	public float updatePriceNego(float previousSuppOffer, float currentSuppOffer, float previousNegoOffer) {
		float delta = Math.abs(currentSuppOffer - previousNegoOffer);
		return previousNegoOffer - (delta / 2);
	}
	//if(agent instanceof Supplier) {
		//System.out.println(oldPrice);
		//if(oldPrice == -1) {
			//oldPrice = buyerPrice;
			//if (buyerPrice >= priceInit) return buyerPrice;
			//return priceInit * 1.5;
		//}
		//double delta = Math.abs(buyerPrice - oldPrice);
		//oldPrice = buyerPrice;
		//double newPrice = sellerPrice - delta;
		//if(newPrice < priceInit) return sellerPrice - (delta / 2);
		//if(newPrice < priceInit / 2) return -1;
		//return newPrice;

}
