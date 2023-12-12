package fr.jdegut.main.strategy;

import fr.jdegut.main.agent.Agent;
import fr.jdegut.main.agent.Supplier;

public class StrategySSS extends Strategy {
	@Override
	public double getInitPrice(Agent agent, double price) {
		if(agent instanceof Supplier) {
			return price * 2;
		}
		return Double.NaN;
	}

	@Override
	public double updatePrice(Agent agent, double priceInit, double sellerPrice, double buyerPrice) {
		if(agent instanceof Supplier) {
			if(buyerPrice >= priceInit) {
				return buyerPrice;
			} else {
				if(buyerPrice < priceInit / 2) return -1;

				double factor = random.nextDouble(5, 11);
				sellerPrice -= sellerPrice / factor;

				if(sellerPrice < priceInit) return -1;
				return sellerPrice;
			}
		}
		return Double.NaN;
	}
}
