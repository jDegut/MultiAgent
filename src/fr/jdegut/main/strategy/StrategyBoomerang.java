package fr.jdegut.main.strategy;

import fr.jdegut.main.agent.Agent;
import fr.jdegut.main.agent.Supplier;

public class StrategyBoomerang extends Strategy{
	private double oldPrice = -1;
	@Override
	public double getInitPrice(Agent agent, double price) {
		if(agent instanceof Supplier) {
			oldPrice = -1;
			return price;
		}
		return -1;
	}

	@Override
	public double updatePrice(Agent agent, double priceInit, double sellerPrice, double buyerPrice) {
		if(agent instanceof Supplier) {
			System.out.println(oldPrice);
			if(oldPrice == -1) {
				oldPrice = buyerPrice;
				if (buyerPrice >= priceInit) return buyerPrice;
				return priceInit * 1.5;
			}
			double delta = Math.abs(buyerPrice - oldPrice);
			oldPrice = buyerPrice;
			double newPrice = sellerPrice - delta;
			if(newPrice < priceInit) return sellerPrice - (delta / 2);
			if(newPrice < priceInit / 2) return -1;
			return newPrice;
		}
		return -1;
	}
}
