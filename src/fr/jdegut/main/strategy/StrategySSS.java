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

	@Override
	public float updatePriceSupp(float previousSuppOffer, float currentNegoOffer, float previousNegoOffer) {
		float factor = random.nextFloat(5, 11);
		previousSuppOffer -= previousSuppOffer / factor;
		return previousSuppOffer;
	}

	@Override
	public float updatePriceNego(float previousSuppOffer, float currentSuppOffer, float previousNegoOffer) {
		float factor = random.nextFloat(5, 11);
		previousNegoOffer += previousNegoOffer / factor;
		return previousNegoOffer;
	}
//				if(buyerPrice < priceInit / 2) return -1;
//				double factor = random.nextDouble(5, 11);
//				sellerPrice -= sellerPrice / factor;
//				if(sellerPrice < priceInit) return -1;
//				return sellerPrice;
//			}
//		}
//		return Double.NaN;
//	}
}
